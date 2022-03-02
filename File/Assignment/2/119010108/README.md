## How To Run My Program

```bash
$ cd assignment2
$ javac HuffmanCompression.java 
$ javac HuffmanDecompression.java 
$ java HuffmanCompression input.txt dictionary.txt compressed.txt
$ java dictionary.txt compressed.txt output.txt
$ cmp input.txt output.txt
```

## Important Declarations

In My Program, please use US-ASCII code to write the input String. 

**DO NOT USE** other coding style, otherwise the program will crashed with **ArrayIndexOutOfBoundsException: 65533** 

## Data Structure I Have Used

* HashMap ( used to store the relationship between weight of each letter and lette name)
* Arraylist (used to store all the root array)
* Tree (used lined list to create a tree)

## Basic Logic of My Progam

### For Compressed part

* First scan the string  list and create a hashmap the store the weight and its letter name
* then scan the hashmap and create a hufftree
* traverse the hufftree to get the binary number, then create a dictionary 
* use the dictionary to compressed the input string 
* output 

### For Decompressed Part

- Create a hash map to store the dictionary letter and its binary code 
- scan the input compressed code and match the dictionary 
- output 

## Demo Output 

<img src="/Users/huangpengxiang/Library/Application Support/typora-user-images/截屏2021-11-19 下午4.36.55.png" alt="截屏2021-11-19 下午4.36.55" style="zoom:50%;" /><img src="/Users/huangpengxiang/Library/Application Support/typora-user-images/截屏2021-11-19 下午4.37.36.png" alt="截屏2021-11-19 下午4.37.36" style="zoom:50%;" />