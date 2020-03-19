import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Cod_Dec{
	private int []g;
	private int []m;
	private int []c;
	private int []a;
	private int []e;
	private int []b;
	private int r;
	private int k;
	private int n;
	private int d;

	public Cod_Dec(int r, int k, int d){//конструктор
		this.r = r;
		g = new int[r + 1];
		this.k = k;
		n = r + k;
		m = new int[k];
		c = new int[r];
		a = new int[n];
		e = new int[n];
		b = new int[n];
		this.d = d;
	}

	public void create_g(String vector){//создание порождающего многочлена
		for(int i = 0; i < r + 1; i++){
			g[i] = (int)vector.charAt(i) % 2;
		}
		print_array(g, "g");
	}

	public void create_m(int []m1){//создание сообщения
		if(m1.length == k){
			for(int i = 0; i < k; i++){
				this.m[i] = m1[i];
			}
		}
		else{
			System.out.println("Error size of vector m");
		}
		print_array(m, "m");
	}

	public void create_c(){//создание массива с, нужен для создания кодового слова
		int []x = new int[r + 1];
		x[0] = 1;
		for(int i = 1; i < x.length; i++){
			x[i] = 0;
		}
		int []tmp = multiply(m, x);
		c = division(tmp);
		print_array(c, "c");
	}

	private int[] multiply(int []m1, int []m2){//перемножение многочленов
		int []res = new int[m1.length + m2.length - 1];
		for(int i = 0; i < m1.length; i++){
			for(int j = 0; j < m2.length; j++){
				res[i + j] += m1[i] * m2[j];
			}
		}
		return res;
	}

	private int[] division(int []tmp){//деление многочленов
		int tmp_deg = tmp.length - 1;
		int i = 0;
		int zero = 0;
		int []tmp1 = new int[1];
		tmp1[0] = 0;
		while(tmp_deg >= r){
			int deg = tmp_deg - r;
			int []x = new int[deg + 1];
			x[0] = 1;
			int []t = multiply(x, g);
			tmp = minus(tmp, t);
			zero = count_zero(tmp);
			if(zero == tmp.length){
				return tmp1;
			}
			else{
				tmp = cut0(tmp);
				tmp_deg = tmp.length - 1;
			}
		}
		return tmp;
	}

	private int[] minus(int []m1, int []m2){//вычитание многочленов
		int []res = new int[m1.length];
		for(int i = 0; i < m1.length; i++){
			res[i] = m1[i] - m2[i];
			if(res[i] == -1) res[i] = 1;
		}
		return res;
	}

	private int count_zero(int []array){//подсчет количества нулей в многочлене
		int count = 0;
		for(int i = 0; i < array.length; i++){
			if(array[i] == 0) count++;
		}
		return count;
	}

	private int[] cut0(int []array){//удаление нулей, которые идут в начале
		int count = 0;
		int i = 0;
		if(array[i] != 1){
			while(array[i] != 1){
				count++;
				i++;
			}
		}
		int []array1 = new int[array.length - count];
		for(int j = 0; j < array1.length; j++){
			array1[j] = array[j + count];
		}
		return array1;
	}

	public void create_a(){//создание кодового слова
		int i = 0;
		int j = 0;
		int tmp = a.length - c.length - m.length;
		while(i < m.length){
			a[i] = m[i];
			i++;
		}
		while(tmp > 0){
			a[i] = 0;
			i++;
			tmp--;
		}
		while(j < c.length){
			a[i] = c[j];
			i++;
			j++;
		}
		print_array(a, "a");
	}

	public int[] get_a(){//получить кодовое слово
		return a;
	}

	public double upper_bound(double p){//подсчет значения верхней ошибки
		double pe = 0;
		double sum = 0;
		for(int i = 0; i < d - 1; i++){
			sum += (double)(combination(i, n) * Math.pow(p, i) * Math.pow((1 - p), (n - i)));
		}
		pe = 1 - sum;
		System.out.println("Pe+ = " + pe);
		return pe;
	}

	private double combination(int a, int b){//формула сочетания
		double res = 0;
		res = (double) (fact(b) / (fact(a) * fact(b - a)));
		return res;
	}

	private int fact(int a){//подсчет факториала
		int sum = 1;
		for(int i = 2; i <= a; i++){
			sum *= i;
		}
		return sum;
	}

	public double exact_value(ArrayList<int[]> list, double p_bit){//подсчет точного значения ошибки
		int []array = new int[n];
		int count = 0;
		double sum = 0;
		for(int i = 0; i < array.length; i++){
			count = code_word_weight(list.get(i));
			for(int j = 0; j < n; j++){
				if(count == j){
					array[j]++;
					break;
				}
			}
		}
		for(int i = d; i < n; i++){
			sum += (double)(array[i] * Math.pow(p_bit, i) * Math.pow((1 - p_bit), (n - i)));
		}
		System.out.println("Pe = " + sum);
		return sum;
	}

	private int code_word_weight(int []array){//подсчет веса кодового слова
		int count = 0;
		for(int i = 0; i < array.length; i++){
			if(array[i] == 1) count++;
		}
		return count;
	}

	private void print_array(int []array, String str){//вывод промежуточных значений
		System.out.print(str + " = (");
		for(int i = 0; i < array.length; i++){
			System.out.print(array[i]);
		}
		System.out.print(")");
		System.out.print("\n");
	}

	public void print_to_csv(double []array1, double []array2) throws Exception{//вывод в csv файл для построения графика
    	PrintWriter writer = new PrintWriter(new File("data.csv"));
    	StringBuilder sb = new StringBuilder();
    	sb.append("p_bit");
    	sb.append(";");
    	sb.append("pe");
    	sb.append("\n");
    	for(int i = 0; i < array1.length; i++){
    		sb.append(array2[i]);
    		sb.append(";");
    		sb.append(array1[i]);
    		sb.append("\n");
    	}
    	writer.write(sb.toString());
    	writer.close();
	}
}