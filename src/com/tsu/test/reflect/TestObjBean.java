package com.tsu.test.reflect;

public class TestObjBean implements TestObjInterface{

    private int age = 12;
    private String name = "Tsu";

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestObjBean(){

    }

    public TestObjBean(int age , String name){
        this.age = age;
        this.name = name;
    }

    private TestObjBean(String name , int age){
        this.age = age;
        this.name = name;
    }

    public TestObjBean(String name){
        this.name = name;
    }

    public TestObjBean(int age){
        this.age = age;
    }

    @Override
    public void onManClick() {

    }

    @Override
    public void onWomenClick() {

    }

    private void show(String string){
        Log.d("Name : "+name+" , age : "+age+" , str : "+string);
       //return "Name : "+name+" , age : "+age+" , str : "+string;
    }

    @Override
    public String toString() {
        return "TestObjBean{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
