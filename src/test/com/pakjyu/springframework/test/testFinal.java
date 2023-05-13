package com.pakjyu.springframework.test;

import org.junit.Assert;
import org.junit.Test;

public class testFinal {
    @Test
    public void testChangeBaseType(){
        int i = 1;
        System.out.println("修改前：i = " + i);
        changeBaseTypeByNormal(i);
        System.out.println("修改后：i = " + i);
    }

    @Test
    public void testChangeReferenceType(){
        //测试正常声明
        SubClass subClassA = new SubClass("第一次赋值");
        System.out.println("修改前 = " + subClassA);
        changeReferenceTypeByFinal(subClassA);
        System.out.println("修改后 = " + subClassA);

        //测试final声明
        final SubClass subClassB = new SubClass("第一次赋值");
        System.out.println("修改前 = " + subClassB);
        changeReferenceTypeByFinal(subClassB);
        System.out.println("修改后 = " + subClassB);
    }

    private void changeReferenceTypeByFinal(final SubClass subClass){
        subClass.name = "第二次赋值";
        //静态编译报错
        //subClass = new SubClass("第二次赋值");
    }

    class SubClass {
        String name;
        public SubClass(String name) {this.name = name;}

        @Override
        public String toString() {
            return "SubClass{" +
                    "name='" + name + '\'' +
                    '}';
        }
    };


    private void changeBaseTypeByNormal(int i){
        i = 100;
    }
    private void changeBaseTypeByFinal(final int i){
        //静态编译报错
        //i = 100;
    }
}
