package tester.ifs;

import util.ExcelUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author lijun
 * @Date 2020年6月28日 20:22:36
 * @Description //TODO
 */
public class MOQRevise {

    private static int times = 1;

    public static void main(String[] args) {
        Map<String, SkuStoryQuantity> skuStoryQuantityMap = getTestDatas();

        //获取所有sku集合
        ArrayList<String> skuList = new ArrayList<>(skuStoryQuantityMap.keySet());
        //将sku从小到大排序
        Collections.sort(skuList);

        //整款色最小起订量M
        int allMOQ = 0;
        //整款色库存数量A
        int allStockQuantity = 0;
        //总缺货需求数量Q
        int allAQuantity = 0;
        //整款色7天销量N
        int allWeekSales = 0;
        //需求总数Z
        int allStoryQty = 0;

        //再次摊分所需数据
        //需第二次分摊数量needSecondQtyF
        float needSecondQtyF = 0;
        //需第二次分摊的sku
        ArrayList<String> needCutSkuList = new ArrayList<>();


        //遍历skuStoryQuantityMap获取以上数据
        for (Map.Entry<String, SkuStoryQuantity> entry : skuStoryQuantityMap.entrySet()) {
            allMOQ += entry.getValue().getMOQ();
            if (entry.getValue().getStockQuantity() > entry.getValue().getOrderQuantity()) {
                allStockQuantity += (entry.getValue().getStockQuantity() - entry.getValue().getOrderQuantity());
            }
            allAQuantity += entry.getValue().getAStoryQuantity();
            allWeekSales += entry.getValue().getWeekSales();
            allStoryQty += entry.getValue().getAStoryQuantity();
            allStoryQty += entry.getValue().getBStoryQuantity();
            allStoryQty += entry.getValue().getCStoryQuantity();
        }
        System.out.println("【M:" + allMOQ + "】【A:" + allStockQuantity + "】【Q:" + allAQuantity + "】【N:" + allWeekSales + "】");
        if (0.3f >= ((float) allStoryQty / allMOQ) || allStoryQty > allMOQ) {
            System.out.println("总需求数量:" + allStoryQty + ";最小起订量：" + allMOQ + ";【" + allStoryQty + "/" + allMOQ + "=" + (float) allStoryQty / allMOQ + "】不满足分摊规则，不分摊!");
        } else {
            int X = allMOQ + allStockQuantity - allAQuantity;
            System.out.println("----------------第1次分摊-----------------");
            for (Map.Entry<String, SkuStoryQuantity> entry : skuStoryQuantityMap.entrySet()) {
                float n1 = (float) entry.getValue().getWeekSales() / allWeekSales;

                int realStockQuantity = Math.max((entry.getValue().getStockQuantity() - entry.getValue().getOrderQuantity()), 0);
                float firstCut = X * n1 - realStockQuantity - entry.getValue().getBStoryQuantity() - entry.getValue().getCStoryQuantity();
                System.out.println(entry.getValue().getSku() + "第" + times + "次分摊结果：" + firstCut + "，销量占比:" + n1);
                //保存第一次分摊结果
                entry.getValue().setFirstCutQuantity(firstCut);

                //第二次分摊需要的数据
                if (firstCut < 0) {
                    needSecondQtyF += firstCut;
                } else {
                    needCutSkuList.add(entry.getValue().getSku());
                }
            }

            //第N次分摊
            if (0 != needSecondQtyF) {
                againCut(needSecondQtyF, needCutSkuList, skuStoryQuantityMap);
            }


            System.out.println("----------------最终分摊结果-----------------");
            for (String sku : skuList) {
                if (skuStoryQuantityMap.get(sku).getFirstCutQuantity() < 0) {
                    skuStoryQuantityMap.get(sku).setFinalQuantity(0);
                } else {
                    skuStoryQuantityMap.get(sku).setFinalQuantity((int) Math.ceil(skuStoryQuantityMap.get(sku).getFirstCutQuantity()));
                }
                int finalCQty = skuStoryQuantityMap.get(sku).getCStoryQuantity() + skuStoryQuantityMap.get(sku).getFinalQuantity();
                System.out.println(skuStoryQuantityMap.get(sku).getSku() + "最终分摊数量：" + skuStoryQuantityMap.get(sku).getFinalQuantity() + "，需求C数量：" + finalCQty);
            }
        }
    }

    /**
     * 从excel获取初始数据
     *
     * @return SkuStoryQuantity对象
     */

    public static Map<String, SkuStoryQuantity> getTestDatas() {

        String sku = "";
        //A需求数量
        int aStoryQuantity = 0;
        //B需求数量
        int bStoryQuantity = 0;
        //C需求数量
        int cStoryQuantity = 0;
        //单个sku的MOQ
        int MOQ = 0;
        //库存
        int stockQuantity = 0;
        //90天订单占用量
        int orderQuantity = 0;
        //前7天真实销量
        int weekSales = 0;

        Map<String, SkuStoryQuantity> skuTestDatasMap = new HashMap<>();

        //获取excel数据
        // String path = "/数据准备.xlsx";
        String path = "C:\\Users\\Administrator\\Desktop\\ifs\\IFS-2379\\数据准备.xlsx";
        Object[][] datas = ExcelUtil.readExcelLocal(path, "0", "2", "7", "2", "9");

        //将数据装进map
        for (Object[] data : datas) {
            for (int j = 0; j < data.length; j++) {
                switch (j) {
                    case 0:
                        sku = data[j].toString();
                        break;
                    case 1:
                        aStoryQuantity = Integer.parseInt(data[j].toString());
                        break;
                    case 2:
                        bStoryQuantity = Integer.parseInt(data[j].toString());
                        break;
                    case 3:
                        cStoryQuantity = Integer.parseInt(data[j].toString());
                        break;
                    case 4:
                        MOQ = Integer.parseInt(data[j].toString());
                        break;
                    case 5:
                        stockQuantity = Integer.parseInt(data[j].toString());
                        break;
                    case 6:
                        orderQuantity = Integer.parseInt(data[j].toString());
                        break;
                    case 7:
                        weekSales = Integer.parseInt(data[j].toString());
                        break;
                }
            }
            SkuStoryQuantity skuStoryQuantity = new SkuStoryQuantity();
            skuStoryQuantity.setSku(sku);
            skuStoryQuantity.setAStoryQuantity(aStoryQuantity);
            skuStoryQuantity.setBStoryQuantity(bStoryQuantity);
            skuStoryQuantity.setCStoryQuantity(cStoryQuantity);
            skuStoryQuantity.setMOQ(MOQ);
            skuStoryQuantity.setStockQuantity(stockQuantity);
            skuStoryQuantity.setOrderQuantity(orderQuantity);
            skuStoryQuantity.setWeekSales(weekSales);
            skuTestDatasMap.put(sku, skuStoryQuantity);
        }
        return skuTestDatasMap;
    }

    /**
     * 多次分摊结果
     *
     * @param needSecondQtyF
     * @param needCutSkuList
     * @param skuStoryQuantityMap
     */
    private static void againCut(Float needSecondQtyF, ArrayList<String> needCutSkuList, Map<String, SkuStoryQuantity> skuStoryQuantityMap) {

        times++;
        float needAgainQty = Math.abs(needSecondQtyF);
        int needCutWeekSales = 0;
        for (String needCutSku : needCutSkuList) {
            needCutWeekSales += skuStoryQuantityMap.get(needCutSku).getWeekSales();
        }

        System.out.println("----------------第" + times + "次分摊-----------------");
        System.out.println("第" + times + "次分摊总数：" + needAgainQty);
        float needAgainCutQty = 0;
        ArrayList<String> needAgainCutSkuList = new ArrayList<>();
        for (String needCutSku : needCutSkuList) {
            float n = (float) skuStoryQuantityMap.get(needCutSku).getWeekSales() / needCutWeekSales;
            float againCut = needAgainQty * n;
            System.out.println(skuStoryQuantityMap.get(needCutSku).getSku() + "第" + times + "次分摊数量：" + againCut + "，销量占比：" + n);
            float finalQuantityTemp = skuStoryQuantityMap.get(needCutSku).getFirstCutQuantity() - againCut;
            skuStoryQuantityMap.get(needCutSku).setFirstCutQuantity((int) Math.ceil(finalQuantityTemp));
            if (finalQuantityTemp < 0) {
                needAgainCutQty += finalQuantityTemp;
            } else {
                needAgainCutSkuList.add(needCutSku);
            }
        }
        if (0 != needAgainCutQty) {
            againCut(needAgainCutQty, needAgainCutSkuList, skuStoryQuantityMap);
        }
    }
}
