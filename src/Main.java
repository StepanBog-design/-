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
		int d = sc.nextInt();//длина хемин
		double []p_bit = {0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1};//массив ошибок на бит для подсчета точных значение ошибки
		double []p = new double [p_bit.length];//значение ошибки для подсчета верхней границы ошибки 
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
		for (int j = 0; j < p_bit.length; j++){
			p[j] = c[1].upper_bound(p_bit[j]);//верхняя граница ошибки
		}
		for(int i = 0; i < p_bit.length; i++){
			pe[i] = c[1].exact_value(list, p_bit[i]);//точное значение ошибки
		}	
		c[1].print_to_csv(pe, p_bit, "exact_value");
		c[1].print_to_csv(p, p_bit, "upper_bound");
	}

	public static int[] get_m(int value, int i, int []m){//перевод числа в двоичную систему для построения всех кодовых слов
		int b = m.length;   
        while(value !=0 ) {  
            m[b - 1] = value%2;    
            value = value/2;
            b--;  
        }  
		return m;
	}
}