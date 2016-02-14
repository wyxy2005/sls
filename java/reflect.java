
JAVA反射使用手记
        本篇文章为在工作中使用JAVA反射的经验总结，也可以说是一些小技巧，以后学会新的小技巧，会不断更新。本文不准备讨论JAVA反射的机制，网上有很多，大家随便google一下就可以了。
        在开始之前，我先定义一个测试类Student，代码如下：
[Java] view plaincopy
package chb.test.reflect;  
  
public class Student {  
    private int age;  
    private String name;  
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
      
    public static void hi(int age,String name){  
        System.out.println("大家好，我叫"+name+"，今年"+age+"岁");  
    }  
}<pre></pre>  
一、JAVA反射的常规使用步骤
    反射调用一般分为3个步骤：
得到要调用类的class
得到要调用的类中的方法(Method)
方法调用(invoke)
     代码示例：
[Csharp] view plaincopy
Class cls = Class.forName("chb.test.reflect.Student");  
Method m = cls.getDeclaredMethod("hi",new Class[]{int.class,String.class});  
m.invoke(cls.newInstance(),20,"chb");<pre></pre>  
二、方法调用中的参数类型
        在方法调用中，参数类型必须正确，这里需要注意的是不能使用包装类替换基本类型，比如不能使用Integer.class代替int.class。
       如我要调用Student的setAge方法，下面的调用是正确的：
 
[Java] view plaincopy
Class cls = Class.forName("chb.test.reflect.Student");  
Method setMethod = cls.getDeclaredMethod("setAge",int.class);  
setMethod.invoke(cls.newInstance(), 15);<pre></pre>  
 
       而如果我们用Integer.class替代int.class就会出错，如：
[Java] view plaincopy
Class cls = Class.forName("chb.test.reflect.Student");  
Method setMethod = cls.getDeclaredMethod("setAge",Integer.class);  
setMethod.invoke(cls.newInstance(), 15);<pre></pre>  
 
       jvm会报出如下异常：
[HTML] view plaincopy
java.lang.NoSuchMethodException: chb.test.reflect.Student.setAge(java.lang.Integer)  
    at java.lang.Class.getDeclaredMethod(Unknown Source)  
    at chb.test.reflect.TestClass.testReflect(TestClass.java:23)<pre></pre>  
 
三、static方法的反射调用
 
       static方法调用时，不必得到对象示例，如下：
[Java] view plaincopy
Class cls = Class.forName("chb.test.reflect.Student");  
Method staticMethod = cls.getDeclaredMethod("hi",int.class,String.class);  
staticMethod.invoke(cls,20,"chb");//这里不需要newInstance  
//staticMethod.invoke(cls.newInstance(),20,"chb");<pre></pre>  
四、private的成员变量赋值
    如果直接通过反射给类的private成员变量赋值，是不允许的，这时我们可以通过setAccessible方法解决。代码示例：
[Java] view plaincopy
Class cls = Class.forName("chb.test.reflect.Student");  
Object student = cls.newInstance();//得到一个实例  
Field field = cls.getDeclaredField("age");  
field.set(student, 10);  
System.out.println(field.get(student));<pre></pre>  
 
     运行如上代码，系统会报出如下异常：
[HTML] view plaincopy
java.lang.IllegalAccessException: Class chb.test.reflect.TestClass can not access a member of class chb.test.reflect.Student with modifiers "private"  
    at sun.reflect.Reflection.ensureMemberAccess(Unknown Source)  
    at java.lang.reflect.Field.doSecurityCheck(Unknown Source)  
    at java.lang.reflect.Field.getFieldAccessor(Unknown Source)  
    at java.lang.reflect.Field.set(Unknown Source)  
    at chb.test.reflect.TestClass.testReflect(TestClass.java:20)<pre></pre>  
    解决方法：
[Java] view plaincopy
Class cls = Class.forName("chb.test.reflect.Student");  
Object student = cls.newInstance();  
Field field = cls.getDeclaredField("age");  
field.setAccessible(true);//设置允许访问  
field.set(student, 10);  
System.out.println(field.get(student));<pre></pre>  
    其实，在某些场合下(类中有get,set方法)，可以先反射调用set方法，再反射调用get方法达到如上效果，代码示例：
[Java] view plaincopy
Class cls = Class.forName("chb.test.reflect.Student");  
Object student = cls.newInstance();  
  
Method setMethod = cls.getDeclaredMethod("setAge",Integer.class);  
setMethod.invoke(student, 15);//调用set方法  
              
Method getMethod = cls.getDeclaredMethod("getAge");  
System.out.println(getMethod.invoke(student));//再调用get方法<pre></pre>  
