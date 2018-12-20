import java.io.*;
import java.util.*;
public class define_global{
    public static void main(String[] args) throws Exception{
		String path_in = "./z.c语言代码输入.txt";
		String anal=new analyzer().answer(path_in);
		String[] t=anal.split("\n");
		String[] step, i, C, S, c, k, p;
		int n=0;
		String line=t[n];
		step = line.substring(1).split(" ");
		n++;
		line=t[n];
		i = line.substring(3, line.length() - 1).replace(" ", "").split(",");
		n++;
		line=t[n];
		C = line.substring(3, line.length() - 1).replace(" ", "").split(",");
		n++;
		line=t[n];
		S = line.substring(3, line.length() - 1).replace(" ", "").split(",");
		n++;
		line=t[n];
		c = line.substring(3, line.length() - 1).replace(" ", "").split(",");
		n++;
		line=t[n];
		k = line.substring(3, line.length() - 1).replace(" ", "").split(",");
		n++;
		line=t[n];
		p = new String[2];
		p[0]=",";
		p[1]=";";
		table tb=new table();

		table.func s=tb.new func();
		s.name="test";
		List<String> xctp=new ArrayList<String>();
		xctp.add("int");xctp.add("int");
		List<String> xcname=new ArrayList<String>();
		xcname.add("d");xcname.add("f");
		s.xctp=xctp;
		s.xcname=xcname;
		table.var v=tb.new var();
		v.name="d";
		v.tp="int";
		v.ofad=0;
		v.other=-1;
		s.vt.add(v);
		v=tb.new var();
		v.name="f";
		v.tp="int";
		v.ofad=1;
		v.other=-1;
		s.vt.add(v);
		tb.pfinfl.add(s);

		v=tb.new var();
		v.name="e";
		v.tp="int";
		v.ofad=0;
		v.other=-1;
		tb.synbl.add(v);

		List<String> vall=new ArrayList<String>();
		vall.add("main");
		vall.add("test");
		tb.vall=vall;

        new define_global().answer(step, i, C, S, c, k, p, tb);
	}
    void answer(String[] step1, String[] i1, String[] C1, String[] S1, String[] c1, String[] k1, String[] p1, table tb){
		String[] step, i, C, S, c, k, p;
		step=step1;//token序列
        i=i1;//变量
		C=C1;//字符
		S=S1;//字符串
		c=c1;//数字常量
		k=k1;//关键字
		p=p1;//符号

        //新建的临时变量，就直接加进总表就好了
        //对于进来的句子，先判断是否有逗号，来判别是几个变量
		String tp=k[Integer.parseInt(step[0].substring(3,4))];//进来的语句第一个都是类型如int
		List<String> name=new ArrayList<String>();
		List<Integer> other=new ArrayList<Integer>();
		if(tp.equals("int")||tp.equals("char")){//对int或者char的定义，other是"_"
			for(int j=1;j<step.length;j++){
			//找逗号判断几个变量，如果遇到逗号或分号，则变量在逗号或分号前一个
				if(step[j].substring(1,2).equals("p")){
					if(p[Integer.parseInt(step[j].substring(3,4))].equals(",")||p[Integer.parseInt(step[j].substring(3,4))].equals(";")){
						name.add(i[Integer.parseInt(step[j-1].substring(3,4))]);
						other.add(-1);
					}
				}
			}
		}
		//增添对数组的支持时写这里		

		for(int j=0;j<name.size();j++){//对于这次定义的每个变量
        	table.var thisv=tb.new var();//新建一个var
			thisv.name=name.get(j);
			thisv.tp=tp;
			thisv.other=other.get(j);
			thisv.ofad=getofad(tb.synbl);
			tb.synbl.add(thisv);
		}
        tb.print(tb);
		wt(tb);
    }
    static void  wt(table tb){
		String result = "";
		for(int j=0;j<tb.synbl.size();j++){
			table.var tv=tb.synbl.get(j);
			result=result.concat(tv.name).concat(" ").concat(tv.tp).concat(" ").concat(String.valueOf(tv.ofad)).concat(" ").concat(String.valueOf(tv.other)).concat("\n");
		}
		result=result.concat("\n");
		for(int j=0;j<tb.pfinfl.size();j++){
			table.func tf=tb.pfinfl.get(j);
			List<String> xctp=tf.xctp;
			List<String> xcname=tf.xcname;
			List<table.var> vt;
			result=result.concat(tf.name).concat("\n");
			for(int jj=0;jj<xctp.size();jj++){
				result=result.concat(xctp.get(jj)).concat(" ").concat(xcname.get(jj)).concat("\n");
			}
			vt=tf.vt;
			for(int jj=0;jj<vt.size();jj++){
				result=result.concat(vt.get(jj).name).concat(" ").concat(vt.get(jj).tp).concat(" ").concat(String.valueOf(vt.get(jj).ofad)).concat(" ").concat(String.valueOf(vt.get(jj).other)).concat("\n");
			}
			result=result.concat("\n");
			for(int jj=0;jj<tb.vall.size();jj++){
				result=result.concat(tb.vall.get(jj)).concat(" ");
			}
		}
		try {
			File writename = new File("./z.符号表.txt");
			writename.createNewFile();
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(writename),"UTF-8");
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(result);
			out.flush();
			out.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	static int getofad(List<table.var> vt){
		//求偏移地址：如果vt.size()为0，则偏移地址为0，
		//如果vt.size()不为0，就去看看上一条var的类型是否是int[]，
		//如果是int[]，那么偏移地址为上一条var的other+ofad
		//如果不是数组而是int或者char，那么偏移地址为ofad+1
		if(vt.size()==0) return 0;
		if(vt.get(vt.size()-1).tp.equals("int[]")) return vt.get(vt.size()-1).other+vt.get(vt.size()-1).ofad;
		else return vt.get(vt.size()-1).ofad+1;
	}
}