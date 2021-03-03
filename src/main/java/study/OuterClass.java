package study;


/**
 * @author 作者 lijun:
 * @Date 2021年01月21日 15:43:02
 * @ClassName Outer
 * @Description 四种内部类区别
 * @Version 1.0
 */
public class OuterClass {

    private String memberVar = "调用了成员变量";
    private static String staticVar = "调用了静态变量";

    //静态方法
    public static void staticAction(String user) {
        System.out.println(String.format("%s 调用了静态方法", user));
    }

    //实例方法
    public void memberAction(String user) {
        System.out.println(String.format("%s 调用了静态方法", user));
    }

    public static class StaticInnerClass {
        /**
         * 静态内部类
         */
        public void innerMethod() {
            System.out.println("-----------静态内部类------------");
            //只能访问外部类的静态变量和方法
            System.out.println(String.format("%s %s", this.getClass().getName(), staticVar));
            staticAction(this.getClass().getName());
            //不能访问外部类的实例变量和方法
            //System.out.println(String.format("%s %s", this.getClass().getName(), memberVar));
            //memberAction(this.getClass().getName());
        }
    }

    public class MemberInnerClass {
        /**
         * 成员内部类
         */
        public void innerMethod() {
            System.out.println("-----------成员内部类------------");
            //可以访问外部类的实例变量和方法
            System.out.println(String.format("%s %s", this.getClass().getName(), staticVar));
            staticAction(this.getClass().getName());
            System.out.println(String.format("%s %s", this.getClass().getName(), memberVar));
            memberAction(this.getClass().getName());
        }
    }

    public void outerMethod(final int param) {
        System.out.println("-----------方法内部类------------");
        final String str = "jyz";
        class MethodInnerClass {
            /**
             * 方法内部类(只能在定义的方法内被使用，外部无法访问)
             */
            public void fun() {
                //可以访问外部类的实例变量和方法
                System.out.println(String.format("%s %s", this.getClass().getName(), staticVar));
                staticAction(this.getClass().getName());
                //根据外部方法决定，是否可以访问外部类的实例变量和方法,method有static修饰以下两行就无法访问
                System.out.println(String.format("%s %s", this.getClass().getName(), memberVar));
                memberAction(this.getClass().getName());
                //可以直接访问方法的参数和局部变量
                System.out.println("param :" + param);
                System.out.println("str :" + str);
            }
        }
        //只能在定义的方法method内被使用
        MethodInnerClass inner = new MethodInnerClass();
        inner.fun();
    }

    StaticInnerClass m = new StaticInnerClass() {
        /**
         * 匿名内部类（特殊）：创建对象的同时定义类
         * new 父类(参数列表){
         *      匿名内部类实现部分
         * }
         * 匿名内部类只能被使用一次，用来创建一个对象。没有名字，没有构造方法
         * 匿名内部类能做的，方法内部类也能做，但如果对象只会创建一次，且不需要构造方法来接收参数，则可使用匿名内部类，这样代码上更为简洁
         */
        public void anonymousInnerClass() {
            System.out.println("-----------匿名内部类------------");
            //可以访问外部类的实例变量和方法
            System.out.println(String.format("%s %s", this.getClass().getName(), staticVar));
            staticAction(this.getClass().getName());
            //根据外部方法决定，是否可以访问外部类的实例变量和方法,method有static修饰以下两行就无法访问
            System.out.println(String.format("%s %s", this.getClass().getName(), memberVar));
            memberAction(this.getClass().getName());
        }
    };
}
