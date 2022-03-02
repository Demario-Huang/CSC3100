# README



### How to Run My Progam

```bash
$ javac MathExpr.java
$ javac TestMathExpr.java
$ java TestMathExpr inputA.txt output.txt
$ cmp output.txt outputA.txt
## you may need to type tr -d '\r' <outputS.txt> new.txt then change outputA.txt to new.txt
## if you use the UNIX system
```



### Decelartion of Valid And Invalid

In my program, The invalid situation I've handle include:

```markdown
# Type Invalid
1. type something that unknow (such as: cat, dog, hello, etc)
2. incorrectly use of space (such as: 0.  3, 3. 2)
3. incorrectly use of '.' (such as: .021)
4. incorectly use of '(' or ')' ( such as sin((20) )

# Math Invalid 
1. denominator is 0  (such as 0.0/0.0)
2. the number is sqrt() is nagetive (such as sqrt(-1) )
3. didn't use operator or miss a operator  (such as: 2.5 4.0 )


```



### Basic Logic of My Program

- First Scan the string and divide them into operators and numbers , create a array store them

  (**If the input of the string is not valid, then it will return a signal and the main function will return invalid **)

- Then use the array before to do the second scan, this time change the infinx expression to suffix expression by **using stack**,  store them into a new array.

- Then scan the previous array,  **using stack**  to compute them.

  

### Main Functions 

```java
public static boolean isOper(String str)
  
public static boolean isNum(char ch)
  
public static int getPriority(String str)
  
public static String[] ToArray(String str)

public static String[] ToPoster(String[] arr) 
  
public static int[] Compute(String[] arr)
```





### Some Demo output srceenshot 

![截屏2021-10-16 下午4.49.31](/Users/huangpengxiang/Desktop/截屏2021-10-16 下午4.49.31.png)



![截屏2021-10-16 下午4.49.50](/Users/huangpengxiang/Library/Application Support/typora-user-images/截屏2021-10-16 下午4.49.50.png)



