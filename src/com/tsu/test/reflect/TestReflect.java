package com.tsu.test.reflect;

import java.lang.reflect.*;

public class TestReflect {

    public static void main(String [] args){
        //通过一个对象获取完整的包名和类名
        //getPackageNameAndClassNameByObj();

        //获取类的名字或者类的包名获取类的包名和名字
        //getClassNameOrPackageName();

        //获取父类和实现的接口
        //getSuperClassAndInterface();

        //获取属性并类型
        //getFieldAndType();

        //获取一个类的全部方法
        //getAllMethod();

        //获取全部构造方法
        //getClassConstructors();

        //获取指定构造方法并生成对象
        //getCustomConstructor();

        //调用私有方法
        //invokePrivateMethod();

        //设置私有属性
        setPrivateField();
    }

    /**
     * 通过一个对象获取完整的包名和类名
     */
    public static void getPackageNameAndClassNameByObj(){
        TestObjBean testObjBean = new TestObjBean();
        Log.d("包名 ： "+testObjBean.getClass().getPackageName());
        Log.d("类名 ： "+testObjBean.getClass().getSimpleName());
        Log.d("典型的名字 : "+testObjBean.getClass().getCanonicalName());
        Log.d("类型的名字 : "+testObjBean.getClass().getTypeName());
        Log.d("描述 : "+testObjBean.getClass().descriptorString());
        Log.d("反编译 : "+testObjBean.getClass().toGenericString());
    }

    /**
     * 获取类的名字或者类的包名获取类的包名和名字
     */
    public static void getClassNameOrPackageName(){
        Class<?> class1 = null;
        Class<?> class2 = null;
        Class<?> class3 = null;

        try{
            class1 = Class.forName("com.tsu.test.reflect.TestObjBean");
            class2 = new TestObjBean().getClass();
            class3 = TestObjBean.class;
            Log.d("包名和类名1"+class1.getName());
            Log.d("包名和类名2"+class2.getName());
            Log.d("包名和类名3"+class3.getName());
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取父类和实现的接口
     */
    public static void getSuperClassAndInterface(){
        Class<?> clazz = null;
        Class<?> parentClass = null;
        try{
            clazz = Class.forName("com.tsu.test.reflect.TestObjBean");

            parentClass = clazz.getSuperclass();
            Log.d("父类是 : "+parentClass.getName());
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        Class<?> [] inters = clazz.getInterfaces();
        for(Class<?> inter : inters ){
            Log.d("inter : "+ inter.getName());
        }
    }

    /**
     * 获取一个类的所有变量及其权限和类型
     */
    public static void getFieldAndType(){
        Class<?> class1 = null;
        try{
            class1 = Class.forName("com.tsu.test.reflect.TestObjBean");
            Field[] fields = class1.getDeclaredFields();
            for(Field field : fields){
                //获取权限修饰符
                int mo = field.getModifiers();
                String priv = Modifier.toString(mo);
                //获取属性类型
                Class<?> type = field.getType();
                Log.d("属性 ："+field.getName()+" , 权限 : "+priv+" , 类型"+type.getName());
            }
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取一个类的全部方法
     */
    public static void getAllMethod(){
        Class<?> class1 = null;
        try{
            class1 = Class.forName("com.tsu.test.reflect.TestObjBean");
            Method [] methods = class1.getMethods();
            for(Method method : methods){
                Class<?> returnType = method.getReturnType();
                Class<?> [] params = method.getParameterTypes();
                int temp = method.getModifiers();
                Log.d("Method : "+method.getName()+" , Modifier : "+Modifier.toString(temp)+" , return : "+returnType.getName());
                for(Class<?> param : params){
                    Log.d("\tParam : "+param.getName());
                }

                Class<?> [] exces = method.getExceptionTypes();
                for(Class<?> exce : exces){
                    Log.d("\tException : "+exce.getName());
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取全部构造方法
     */
    public static void getClassConstructors(){
        Class<?> class1 = null;
        try{
            class1 = Class.forName("com.tsu.test.reflect.TestObjBean");
            Constructor<?>[] constructors = class1.getDeclaredConstructors();
            for(Constructor<?> constructor : constructors){
                String modifier = Modifier.toString(constructor.getModifiers());
                //Log.d("modifier : "+modifier);
                Class[] paramTypes = constructor.getParameterTypes();
                StringBuilder stringBuilder = new StringBuilder(modifier);
                for(Class type : paramTypes){
                    stringBuilder.append(" : ");
                    stringBuilder.append(type.getSimpleName());
                }
                Log.d(stringBuilder.toString());
            }

        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 获取指定构造方法,调用私有构造方法
     */
    public static void getCustomConstructor(){
        Class<?> class1 = null;
        Class[] params = {String.class , int.class};
        try{
            class1 = Class.forName("com.tsu.test.reflect.TestObjBean");
            Constructor constructor = class1.getDeclaredConstructor(params);
            constructor.setAccessible(true);
            //Log.d(constructor.getName());
            Object obj = constructor.newInstance( "Queenna" , 24);
            Log.d(obj.toString());
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用类的私有方法
     */
    public static void invokePrivateMethod(){
        Class<?> clazz  = null;
        Class [] params = {String.class};
        try{
            clazz = Class.forName("com.tsu.test.reflect.TestObjBean");
            Method method = clazz.getDeclaredMethod("show" , params);
            method.setAccessible(true);
            Object [] args = {"hello reflect !"};

            Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class , int.class});
            constructor.setAccessible(true);
            Object obj = constructor.newInstance( "Queenna" , 24);

            method.invoke(obj , args);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给私有属性赋值
     */
    public static void setPrivateField(){
        Class<?> clazz  = null;
        try{
            clazz = Class.forName("com.tsu.test.reflect.TestObjBean");

            Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class , int.class});
            constructor.setAccessible(true);
            Object obj = constructor.newInstance( "Queenna" , 24);
            Log.d("before : "+obj.toString());
            Field nameField = clazz.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(obj , "June");

            Log.d("after : "+obj.toString());

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
