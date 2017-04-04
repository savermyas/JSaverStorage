package saver.galoismath;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * РљР»Р°СЃСЃ РґР»СЏ СЂР°Р±РѕС‚С‹ СЃ РјР°С‚СЂРёС†Р°РјРё, СЌР»РµРјР°РЅС‚Р°РјРё РєРѕС‚РѕСЂС‹С… СЏРІР»СЏСЋС‚СЃСЏ С‡Р»РµРЅС‹ РїРѕР»СЏ Р“Р°Р»СѓР°
 * @author    Myasnikov Dmitry, Vologda State Technical University, saver_is_not@bk.ru
 */
public class GF8SimpleMatrix implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4732290987108686640L;
	/**
	 * РљРѕР»РёС‡РµСЃС‚РІРѕ СЃС‚РѕР»Р±С†РѕРІ
	 * @uml.property  name="colsNum"
	 */
	int colsNum;
	/**
	 * РљРѕР»РёС‡РµСЃС‚РІРѕ СЃС‚СЂРѕРє
	 * @uml.property  name="rowsNum"
	 */
	int rowsNum;
	/**
	 * Р”Р»РёРЅР° Р±СѓС„РµСЂР° - РЅСѓР¶РЅР° РґР»СЏ СѓС‡РєРѕСЂРµРЅРёСЏ СЂР°Р±РѕС‚С‹ СЃ С„Р°Р№Р»РѕРІРѕР№ СЃРёСЃС‚РµРјРѕР№
	 * @uml.property  name="bufSize"
	 */
	int bufSize;
	/**
	 * РҐРёС‚СЂС‹Р№ РїР»Р°РЅ - С…СЂР°РЅРёС‚СЊ РјР°С‚СЂРёС†Сѓ РІ РїСЂРѕСЃС‚РѕРј РјР°СЃСЃРёРІРµ. Р Р°Р·РґРµР»РµРЅРёРµ РЅР° СЏС‡РµР№РєРё РїСЂРѕРёСЃС…РѕРґРёС‚ Р»РѕРіРёС‡РµСЃРєРё:  [.......................]=> [[......][......][......]],  РіРґРµ РєР°Р¶РґР°СЏ СЃС‚СЂРѕРєР° РІ СЃРІРѕСЋ РѕС‡РµСЂРµРґСЊ СЃРѕСЃС‚РѕРёС‚ РёР· РјР°СЃСЃРёРІРѕРІ Р±Р°Р№С‚РѕРІ, РґР»РёРЅРѕР№ bufLength
	 * @uml.property  name="data"
	 */
	public byte [] data;
	/**
	 * РџРѕР»СѓС‡РµРЅРёРµ СЂР°Р·РјРµСЂР° Р±СѓС„РµСЂР°
	 * @return    Р”Р»РёРЅР° Р±СѓС„РµСЂР°
	 * @uml.property  name="bufSize"
	 */
	public int getBufSize() 
	{
		return bufSize;
	}
	/**
	 * РЈСЃС‚Р°РЅРѕРІРєР° РЅРѕРІРѕРіРѕ Р·РЅР°С‡РµРЅРёСЏ СЂР°Р·РјРµСЂР° Р±СѓС„РµСЂР°
	 * @param bufSize    РќРѕРІС‹Р№ СЂР°Р·РјРµСЂ Р±СѓС„РµСЂР°
	 * @uml.property  name="bufSize"
	 */
	public void setBufSize(int bufSize) 
	{
		this.bufSize = bufSize;
	}
	/**
	 * @return
	 * @uml.property  name="data"
	 */
	public byte[] getData() 
	{
		return data;
	}
	/**
	 * @param  data
	 * @uml.property  name="data"
	 */
	public void setData(byte[] data) 
	{
		this.data = data;
	}
	/**
	 * РџРѕР»СѓС‡РµРЅРёРµ СЃС‚РѕР»Р±С†Р° РјР°С‚СЂРёС†С‹
	 * @param i РќРѕРјРµСЂ Р¶РµР»Р°РµРјРѕРіРѕ СЃС‚РѕР»Р±С†Р°
	 * @return РЎС‚РѕР»Р±РµС† РјР°С‚СЂРёС†С‹ РІ РІРёРґРµ РјР°СЃСЃРёРІР° Р±Р°Р№С‚РѕРІ
	 */
	public byte[] getCol(int i)
	{
		if(i>=this.getColsNum())
		{
			return null;
		}
		byte[] rez = new byte[rowsNum*bufSize];
		int z = 0;
		for(int j = 0; j<rowsNum; j++)
		{
			int startPos = ((colsNum*j+i)*bufSize);
			for(int k = 0; k<bufSize; k++)
			{
				rez[z] = data[startPos+k];
				z++;
			}
		}
		return rez;
	}
	
	public byte[] getRow(int i) 
	{
		
		if(i>=this.getRowsNum())
		{
			return null;
		};

		byte[] rez = new byte[colsNum*bufSize];
		int startPos = i*colsNum*bufSize;
		for(int j = 0; j<rez.length; j++)
		{
			rez[j] = data[startPos+j];
		}
		return rez;	
	}
	public void setCol(int i, byte[] newCol)
	{
		if(i<this.getColsNum())
		{

		}
		{
			int z = 0;
			for(int j = 0; j<rowsNum; j++)
			{
				int startPos = ((colsNum*j+i)*bufSize);
				for(int k = 0; k<bufSize; k++)
				{
					data[startPos+k] = newCol[z];
					z++;
				}
			}	
		}
		
	}
	public void setRow(int i, byte[] newRow)
	{
		
		if(i<this.getRowsNum())
		{
		
		}
		{
			int startPos = i*colsNum*bufSize;
			for(int j = 0; j<newRow.length; j++)
			{
				data[startPos+j] = newRow[j];
			}
		}
		
	}
	/**
	 * @return    РљРѕР»РёС‡РµСЃС‚РІРѕ СЃС‚РѕР»Р±С†РѕРІ РјР°С‚СЂРёС†С‹
	 * @uml.property  name="colsNum"
	 */
	public int getColsNum() 
	{
		return colsNum;
	}
	/**
	 * Р�Р·РјРµРЅРµРЅРёРµ РєРѕР»РёС‡РµСЃС‚РІР° СЃС‚РѕР»Р±С†РѕРІ  РјР°С‚СЂРёС†С‹
	 * @param colsNum    РЈСЃС‚Р°РЅР°РІР»РёРІР°РµРјРѕРµ РєРѕР»РёС‡РµСЃС‚РІРѕ СЃС‚РѕР»Р±С†РѕРІ
	 * @uml.property  name="colsNum"
	 */
	public void setColsNum(int colsNum) 
	{
		this.colsNum = colsNum;
		data = Arrays.copyOf(data, colsNum*rowsNum*bufSize);
	}
	/**
	 * @return    the rowsNum
	 * @uml.property  name="rowsNum"
	 */
	public int getRowsNum() 
	{
		return rowsNum;
	}
	/**
	 * @param rowsNum    РЈСЃС‚Р°РЅР°РІР»РёРІР°РµРјРѕРµ РєРѕР»РёС‡РµСЃС‚РІРѕ СЃС‚СЂРѕРє
	 * @uml.property  name="rowsNum"
	 */
	public void setRowsNum(int rowsNum) 
	{
		this.rowsNum = rowsNum;
		data = Arrays.copyOf(data, colsNum*rowsNum*bufSize); 
	}
	/**
	 * РџСЂРёСЃРІРѕРµРЅРёРµ СЌР»РµРјРµРЅС‚Сѓ РІ "СЏС‡РµР№РєРµ" РЅРѕРІРѕРіРѕ Р·РЅР°С‡РµРЅРёСЏ
	 * @param r РќРѕРјРµСЂ СЃС‚СЂРѕРєРё
	 * @param c РќРѕРјРµСЂ СЃС‚РѕР»Р±С†Р°
	 * @param d РњР°СЃСЃРёРІ Р±Р°Р№С‚РѕРІ, РїРѕРјРµС‰Р°РµРјС‹Р№ РІ "СЏС‡РµР№РєСѓ" СЃ СѓРєР°Р·Р°РЅРЅС‹РјРё РєРѕРѕСЂРґРёРЅР°С‚Р°РјРё
	 */
	public void setItem(int r, int c, byte[] d) 
	{
		int startPos = (r*colsNum+c)*bufSize;
		for(int i = 0; i<bufSize; i++)
		{
			data[startPos+i] = d[i]; 
		}
	}
	public byte[] itemsMult (byte[] a, byte[] b)
	{
		byte[] rez = new byte[a.length];
		for(int i = 0; i< a.length; i++)
		{
			rez[i] = GF8.gf8Mult(a[i], b[i]);
		}
		return rez;
	}
	
	public byte[] itemsAdd (byte[] a, byte[] b)
	{
		byte[] rez = new byte[a.length];
		for(int i = 0; i< a.length; i++)
		{
			rez[i] = GF8.gf8Add(a[i], b[i]);
		}
		return rez;
	}
	
	public byte[] itemsDiv (byte[] a, byte[] b)
	{
		byte[] rez = new byte[a.length];
		for(int i = 0; i< a.length; i++)
		{
			rez[i] = GF8.gf8Div(a[i], b[i]);
		}
		return rez;
	}
	
	/**
	 * @param cols column count
	 * @param rows rows count
	 */
	public GF8SimpleMatrix(int rows, int cols, int d)
	{
		colsNum = cols;
		rowsNum = rows;
		bufSize = d;
		data = new byte [colsNum*rowsNum*bufSize];
	}

	
	/**
	 * @param col column
	 * @param row row
	 */
	public byte[] getItem(int row, int col)
	{
		byte[] rez = new byte[bufSize];
		int startpos = (row*colsNum+col)*bufSize;
		for(int k = 0; k<bufSize; k++)
		{
			rez[k] = data[startpos+k];
		}
		return rez;		
	}
	
	public byte[] itemsSum (byte[] a, byte [] b)
	{
		byte[] rez = new byte[1];
		return rez;
	}
	            
	public String toString()
	{
		String buf = "";
		for (int i=0; i<rowsNum; i++)
		{
			for(int j=0; j<colsNum;j++)
			{
				int startpos = (i*colsNum+j)*bufSize;
				for(int k = 0; k<bufSize; k++)
				{
					try {
						buf = buf+data[startpos+k]+"";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				buf = buf+"	";
			}
			buf = buf + '\n';
		}
		return buf;
	//	return ""+this.getColsNum()+"x"+this.getRowsNum()+"x"+this.getBufSize();
	}
	
	public String toOneString()
	{
		String buf = "";
		
		for(int i = 0; i<data.length; i++)
		{
			buf = buf+data[i];
		}
				
		return buf;
	}
	
	public GF8SimpleMatrix mult (GF8SimpleMatrix b)
	{
		GF8SimpleMatrix rez = new GF8SimpleMatrix(this.getRowsNum(), b.getColsNum(), b.getBufSize());		
		for (int i=0; i<this.getRowsNum(); i++)
		{
			for(int j=0; j<b.getColsNum();j++)
			{
				rez.setItem(i, j, scalarVectorsMult(getRow(i), b.getCol(j)));				
			}
		}
		return rez;		
	}
	
	public byte[] scalarVectorsMult(byte[] a, byte[] b) 
	{
		byte[] rez = new byte[bufSize];
		int elemNum = a.length/bufSize; //СЃС‡РёС‚Р°РµРј РєРѕР»РёС‡РµСЃС‚РІРѕ СЌР»РµРјРµРЅС‚РѕРІ
		for(int j=0; j<elemNum;j++) // РїСЂРѕР±РµРіР°РµРј РѕР±Р° РјР°СЃСЃРёРІР°
		{
			byte[] a1 = new byte[bufSize];
			byte[] b1 = new byte[bufSize];
			for(int k = 0; k<bufSize; k++)
			{
				a1[k] = a[j*bufSize+k];
				b1[k] = b[j*bufSize+k];
			}
			rez = itemsAdd(rez, itemsMult(a1, b1)); 
		}
		return rez;
	}
	
	public void SaveToFiles()
	{
		for (int i = 0; i < this.rowsNum; i++)
		{
			DataOutputStream os = null;
			try 
			{
				os = new DataOutputStream(new FileOutputStream("part"+i+".txt"));
				os.write(this.getRow(i));	
				os.close();
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}				
	}
	public void SaveToFile(String filename)
	{
		DataOutputStream os = null;
		try 
		{
			os = new DataOutputStream(new FileOutputStream(filename));
			os.write(data);
			os.close();
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
				
	}
	
	public GF8SimpleMatrix minor(int row, int col )
	{
		if(this.getColsNum() < 2 || this.getRowsNum() < 2) 
			return null;
		GF8SimpleMatrix rez = new GF8SimpleMatrix (this.rowsNum-1, this.colsNum-1, bufSize);
		int rez_i = 0;
		int rez_j = 0;
		for (int i = 0; i < this.rowsNum; i++)				
		{
			for(int j=0; j<this.colsNum;j++)
			{
				if(i!=row && j!=col)
				{
					rez.setItem(rez_i, rez_j, getItem(i, j));
					rez_j++;
				};		
			};
			rez_j = 0;
			if(i!=row) rez_i++;
		}		
		return rez;
	}	
	
	/**
	 * @return
	 */
	public byte[] det()
	{
		byte[] rez = new byte[bufSize];
		if( (this.getColsNum() == 2) && (this.getRowsNum() == 2) )
			return itemsAdd(itemsMult(getItem(0, 0), getItem(1, 1)), itemsMult(getItem(1, 0), getItem(0, 1)));

		for(int j = 0; j < this.getColsNum(); j++ )
		{
			GF8SimpleMatrix M = this.minor(0, j);			
			byte[] D = M.det();
			byte[] X = this.getItem(0, j);
			X = itemsMult(X,D);
			rez = itemsAdd(rez,X);
		};				
		return rez;		
	}
	
	
	public GF8SimpleMatrix transp()
	{
		GF8SimpleMatrix A = new GF8SimpleMatrix(colsNum,rowsNum,bufSize);
		for (int i = 0; i < rowsNum; i++)				
		{
			for(int j=0; j<this.colsNum;j++)
			{
				A.setItem(j, i, getItem(i, j));
			};
		}		
		return A;
	}
	public GF8SimpleMatrix obr()
	{
		GF8SimpleMatrix A = new GF8SimpleMatrix(this.getRowsNum(),this.getColsNum(), bufSize);		
		byte[] D = this.det();
		boolean dIsNull = false;
		for(int i = 0; i<bufSize; i++)
		{
			dIsNull = dIsNull && (this.data[i]==0);
		}
		///return rez;		
		if(dIsNull)
		{
			return null;
		}
		for(int i = 0; i < A.getRowsNum(); i++ )
			for(int j = 0; j < A.getColsNum(); j++ )
				A.setItem(i, j, this.minor(i, j).det());
		A = A.transp();
		for(int i = 0; i < A.getRowsNum(); i++ )
			for(int j = 0; j < A.getColsNum(); j++ )
				A.setItem(i, j, itemsDiv(A.getItem(i, j), D));
		return A;
	}
	/**
	 *  Р—Р°РїРѕР»РЅРµРЅРёРµ РјР°С‚СЂРёС†С‹ СЃР»СѓС‡Р°Р№РЅС‹РјРё Р±Р°Р№С‚Р°РјРё
	 * */
	public void fillRandom() 
	{
		for(int k = 0; k < data.length; k++)
		{
			data[k]=(byte) (Math.random()*255);
		};
	}
	
	/**
	 *  РЎРѕР·РґР°С‚СЊ РјР°С‚СЂРёС†Сѓ Р’Р°РЅРґРµСЂРјРѕРЅРґР°
	 * */
	public void generateVandermond() 
	{
		for(int i = 0; i<rowsNum; i++)
		{
			byte[] firstCol = new byte[bufSize];
			for(int j = 0; j< bufSize; j++)
			{
				firstCol[j] = 1;
			}
			setItem(i,0,firstCol);
		};		
		if(colsNum>1)
		{
			byte initVal = Byte.MIN_VALUE;
			for(int i = 0; i<rowsNum; i++)
			{
				byte[] secondCol = new byte[bufSize];
				for(int j = 0; j< bufSize; j++)
				{
					if(initVal==0) initVal++;
					secondCol[j] = initVal;
					//secondCol[j] = (byte) ((5*i+7)); //-Byte.MAX_VALUE
					
				}
				initVal++;
				setItem(i,1,secondCol);
			};
		};
		if(colsNum>2)
		for(int i = 0; i<rowsNum; i++)
		{
			setItem(i, 2, itemsMult(getItem(i, 1), getItem(i, 1)));
		};
		if(colsNum>3)
		for(int j = 3; j<colsNum; j++)
		{
			for(int i = 0; i<rowsNum; i++)
			{
				setItem(i, j, itemsMult(getItem(i, j-1), getItem(i, j-2)));
			}
		}
	}
	public GF8SimpleMatrix mult1(GF8SimpleMatrix a1) {
		//public GFMatrix mult1 (GFMatrix b)
		//{
			GF8SimpleMatrix rez = new GF8SimpleMatrix(rowsNum, colsNum, bufSize);
			
			for (int i=0; i<this.getColsNum(); i++)
			{
				rez.setCol(i, a1.multVector(this.getCol(i)));
				//System.gc();
			}
			return rez;		
		}
	private byte[] multVector(byte[] col) 
	{
	
		byte [] rez = new byte[col.length]; //СЂРµР·СѓР»СЊС‚Р°С‚ РїРѕ РґР»РёРЅРµ РґРѕР»Р¶РµРЅ СЃРѕРІРїР°РґР°С‚СЊ СЃ РґР»РёРЅРѕР№ СЃС‚РѕСЂРѕРіРѕ РјРЅРѕР¶РёС‚РµР»СЏ
		//GFVector rez = new GFVector (this.getRowsNum(), this.getDimension());
		for(int i=0; i<this.getRowsNum();i++) //РїСЂРѕР±РµРіР°РµРј РІСЃСЋ С‚Р°Р±Р»РёС†Сѓ
		{
			//GFMember sum = new GFMember(this.dimension);
			byte[] sum = new byte[bufSize]; //СЃСЋРґР° СЃС…РѕСЂРѕРЅСЏРµРј С‚РµРєСѓС‰СѓСЋ СЃСѓРјРјСѓ
			for (int j=0; j<this.getColsNum(); j++)
			{
				byte[] z = new byte[bufSize];
				for(int k = 0; k < bufSize; k++)
				{
					z[k] = col[j*bufSize+k];
				}
				//sum = sum.add(this.getItem(i, j).mult(b.getItem(j)));
				sum = itemsAdd(sum, itemsMult(getItem(i, j), z));
			}
			for(int k = 0; k < bufSize; k++)
			{
				rez[i*bufSize+k] = sum[k];
			}
			//rez.setItem(i, sum);
		}
		return rez;
	}
	public void setDataFromArray(byte[] buf) 
	{
		this.fillRandom();
		for(int i = 0; i<buf.length; i++)
			this.data[i] = buf[i];
		
	}
	public void fixDataLength(int mdataLength) 
	{
		byte[] buf = this.data;
		this.data = new byte[mdataLength];
		this.fillRandom();
		for(int i = 0; i<buf.length; i++)
			this.data[i] = buf[i];
	}
}