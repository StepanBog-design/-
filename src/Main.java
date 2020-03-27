import java.util.ArrayList;
import java.util.Scanner;

public class Main{
	public static void main(String[] args) throws Exception{
		ArrayList<int[]> list = new ArrayList<>();//лист кодовых слов
		Scanner sc = new Scanner(System.in);
		System.out.println("Input r: ");
		int r = sc.nextInt();//степень порождающего многочлена
		System.out.println("Input k: ");
		int k = sc.nextInt();//длина сообщения
		System.out.println("Input d: ");
		int d = sc.nextInt();
		double p = 0.2;//значение ошибки для подсчета верхней границы ошибки 
		double []p_bit = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1};//массив ошибок на бит для подсчета точных значение ошибки
		double []pe = new double[p_bit.length];
		Cod_Dec []c = new Cod_Dec[(int)Math.pow(2, k)];
		for(int i = 1; i < (int)Math.pow(2, k); i++){
			c[i] = new Cod_Dec(r, k, d);
			int []m = new int[k];
			Integer number = new Integer(i);
			m = get_m(number, i, m);//перевод числа из 10 системы счисления в двоичную
			c[i].create_g("1101");//создание порождающего многочлена
			c[i].create_m(m);//создание сообщения
			c[i].create_c();//создание вектора с
			c[i].create_a();//создание кодового слова
			list.add(c[i].get_a());//получение кодового слова
		}
		for(int i = 0; i < list.size(); i++){//вывод всех кодовых слов
			int []array = list.get(i);
			System.out.print("the codeword" + i + " = (");
			for(int j = 0; j < array.length; j++){
				System.out.print(array[j]);
			}
			System.out.println(")");
		}
		p = c[1].upper_bound(p);//верхняя граница ошибки
		for(int i = 0; i < p_bit.length; i++){
			pe[i] = c[1].exact_value(list, p_bit[i]);//точное значение ошибки
		}	
		c[1].print_to_csv(pe, p_bit);
	}

	public static int[] get_m(int value, int i, int []m){//перевод числа в двоичную систему для построения всех кодовых слов
		String number = Integer.toBinaryString(value);
		if(i < 2){
			m[3] = (int)number.charAt(0) % 2;
		}
		if(i >= 2 && i < 4){
			m[2] = (int)number.charAt(1) % 2;
			m[3] = (int)number.charAt(0) % 2;
		}
		if(i >= 4 && i < 8){
			m[1] = (int)number.charAt(2) % 2;
			m[2] = (int)number.charAt(1) % 2;
			m[3] = (int)number.charAt(0) % 2;
			m[0] = 0;
		}
		if(i >= 8 && i < 16){
			m[0] = (int)number.charAt(3) % 2;
			m[1] = (int)number.charAt(2) % 2;
			m[2] = (int)number.charAt(1) % 2;
			m[3] = (int)number.charAt(0) % 2;
		}
		return m;
	}
}