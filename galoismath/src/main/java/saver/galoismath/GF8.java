package saver.galoismath;

/**
 * Класс для работы с полями Галуа GF(8).
 * @author Myasnikov Dmitry, Vologda State Technical University, saver_is_not@bk.ru.
 */
public class GF8
{	 
	/**
	 * Длина кодового слова.
	 */
	final private static int n=255;    // n=2**m-1         		  (длина кодового слова) 
	/**
	 * Несократимый порождающий полином. Cогласно Стандарту ECMA-130: P(x) = x8 + x4 + x3 + x2 + 1 (byte p[]={1, 0, 1, 1, 1, 0, 0, 0, 1 }).
	 */
	final private static int pb=283;
	/**
	 * Таблица для быстрого умножения.
	 */
	final private static byte table_mult[][] = new byte[n+1][n+1];
	/**
	 * Таблица для быстрого деления.
	 */
	final private static byte table_div[][] = new byte[n+1][n+1];
	/**
	 * Инициализация таблиц умножения и деления.
	 */
	final static public void generateGf8()
	{ 	
		for(byte i = Byte.MIN_VALUE; i<=Byte.MAX_VALUE; i++)
		{
			for(byte j = Byte.MIN_VALUE; j<=Byte.MAX_VALUE; j++)
			{
				table_mult[i-Byte.MIN_VALUE][j-Byte.MIN_VALUE] = (byte) gf8MultInit(i,j);
				if(j==Byte.MAX_VALUE) break;
			}
			if(i==Byte.MAX_VALUE) break;
		}		
		for(byte i = Byte.MIN_VALUE; i<=Byte.MAX_VALUE; i++)
		{
			for(byte j = Byte.MIN_VALUE; j<=Byte.MAX_VALUE; j++)
			{
				if(j!=0)
					table_div[i-Byte.MIN_VALUE][j-Byte.MIN_VALUE] = (byte) gf8DivInit(i,j);
				if(j==Byte.MAX_VALUE) break;
			}
			if(i==Byte.MAX_VALUE) break;
		}
	}
	/**
	 * Сложение двух чисел в поле Галуа. Представляет собой операцию xor.
	 * @param a Первое слагаемое.
	 * @param b Первое слагаемое.
	 */
	final static public byte gf8Add(byte a, byte b)
	{
		return (byte) (a^b);
	}
	/**
	 * Нахождение степени числа.
	 * @param x Число, степень которого определяем.
	 * */
	final static private byte findPower(byte x) 
	{
		if(x<0) return 7;
		byte a = 1;
		byte rez = 0;
		while(getInt(x)>=getInt(a))
		{
			a = (byte) (a<<1);
			rez++;
		};
		return --rez;
	}
	/**
	 *  Степени двойки.
	 * 	@param p В какую степень возводим.
	 * */
	final static public int twoPower(int p)
	{
		byte rez = 1;
		for(byte i = 1; i<=p; i++)
		{
			rez = (byte) (rez<<1);
		}
		return rez;
	}
	/**
	 *  Округление числа до ближайшей степени двойки с меньшую сторону.
	 * 	@param p - "округлямое".
	 * */
	final static private byte round(byte p)
	{
		return (byte) twoPower(findPower(p));
	}
	/**
	 *  "Медленное" перемножение чисел. Рекомендуется использовать исключительно для инициализации таблицы умножения. 
	 * 	@param a Первый множитель.
	 * 	@param b Второй множитель.
	 * */
	final static private byte gf8MultInit(byte a, byte b)
	{
		 //понятно, что при произведении байта на байт нам не хватит разрядности, поэтому результат будет скидывать в int
		 int rez;	   
		 if(b == round(b)) rez = getInt(a)<<power(getInt(b)); else //если один из множителей это 2^n
		 if(a == round(a)) rez = getInt(b)<<power(getInt(a)); else //осуществляем сдвиг байтов
		 {
			rez = 0;
			while(getInt(b)>0)
			{				
				rez = rez^(getInt(a)<<power(getInt(b)));
				b = (byte) (b^round(b));				
			}
   		 } 
 		 while(rez>n) //код нахождения отстатка от деления результата 
 			          //на порождающий полином
 			 	 	  //TODO оптимизация 
		 {
 			 int curr_power = power(rez);
 			 int pb_power = power(pb);
 			 int delta_power = curr_power-pb_power;
 			 int curr_x = xp(delta_power); 			 
 			 int curr_proizv = pb<<power(curr_x);
 				 //xtimes(pb,curr_x);
 			 rez = rez^curr_proizv;
		  };
		  return (byte) rez;
	}
	/**
	 *  "Медленное" деление чисел. Рекомендуется использовать исключительно для инициализации таблицы деления. Деление реализовано перебором. 
	 * 	@param a Делимое.
	 * 	@param b Делитель.
	 * 	@throws ArithmeticException при делении на ноль.
	 * */
	final static private byte gf8DivInit(byte a, byte b)
	{
		if (b==0) throw new ArithmeticException("ACHTUNG! ACHTUNG! CAN NOT DIVIDE BY ZERO");
	    byte rez = 0;
	    byte b_1=Byte.MIN_VALUE;
	    while(rez!=1) //ох, щи, как же скорость-то упадёт
	    {
	    	b_1++;
	    	rez = gf8Mult(b,b_1);
	    }
		return (byte) gf8Mult(a,b_1);
	    
	}
	/**
	 *  "Быстрое" умножение чисел. Реализовано табличным методом. Перед использованием этого метода должен быть выполнен generateGf8(). 
	 * 	@param a Первый множитель.
	 * 	@param b Второй множитель.
	 * */
	public static byte gf8Mult(byte a, byte b) {
		return table_mult[a-Byte.MIN_VALUE][b-Byte.MIN_VALUE];
	}
	/**
	 *  "Быстрое" деление чисел. Реализовано табличным методом. Перед использованием этого метода должен быть выполнен generateGf8(). 
	 * 	@param a Делимое.
	 * 	@param b Делитель.
	 *  @throws ArithmeticException при делении на ноль.
	 * */
	public static byte gf8Div(byte a, byte b) 
	{	
		if (a==0)
		{
			throw new ArithmeticException("ACHTUNG! ACHTUNG! GOLAKTEKO OPASNOSTE! CAN NOT DIVIDE BY ZERO");			
		}
		else
		return table_div[a-Byte.MIN_VALUE][b-Byte.MIN_VALUE];
	}
	// Три последних функции - им тут не место. Используются исключительно в функции gf8MultInit. 
	final static private int power(int a) 
	{
		int rez = 0;
		int buf = 1;
		while(buf<=a)
		{
			buf = buf*2;
			rez++;
		};
		rez--;
		return rez;	
	}
	/**
	 * Нахождение степени двойки.
	 * @param p Степень.
	 * @return 2^p.
	 */
	final static private int xp(int p)
	{
		if (p == 0) return 1;
		int rez = 2;
		for(int i = 1; i<p; i++)
		{
			rez = rez*2;
		}
		return rez;
	}
	/**
	 *  Представление байта в диапазоне 0..255 для совершения операций сравнения
	 * 	@param p байт, превращаемый в int
	 *  @return целочисленный тип, равноценный байту 0..255
	 * */
	final static private int getInt(byte p)
	{
		int rez = p;
		if (p<0) rez = p+256;
		return rez;
	}
}

 


