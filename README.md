0.编译实验文件夹是编译课的实验，当前目录下的是编译课的课设
1.analyzer输入c语言代码（目前是输入算数表达式），输出c语言的token序列和iCSckp表
如：

输入：String
2+3*(4+5)

输出：String
 {c,0} {p,0} {c,1} {p,1} {p,2} {c,2} {p,0} {c,3} {p,3}
i:[]
C:[]
S:[]
c:[2.0, 3.0, 4.0, 5.0]
k:[main, void, if, else, while, for, int, char, string]
p:[+, *, (, )]


2.exp_four输入算数表达式的token序列和iCSckp表，输出算数表达式的四元式中间代码
3.optimization输入算数表达式的四元式中间代码，输出算数表达式的优化后的四元式
4.object_code输入算数表达式的优化后的四元式，输出算数表达式的汇编代码
5.Main调用1,2,3,4，也就是输入算数表达式的c语言代码，输出算数表达式的汇编代码