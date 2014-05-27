package com.health.digitalmedical.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinToolkit
{
	public static String cn2FirstSpell(String paramString)
	{
		StringBuffer localStringBuffer = new StringBuffer();
		char[] arrayOfChar = paramString.toCharArray();
		HanyuPinyinOutputFormat localHanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
		localHanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		localHanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		for (int i = 0; i < arrayOfChar.length; i++)
		{
			if (arrayOfChar[i] > 128)
			{
				try
				{
					String[] arrayOfString = PinyinHelper.toHanyuPinyinStringArray(arrayOfChar[i], localHanyuPinyinOutputFormat);
					if (arrayOfString != null)
						localStringBuffer.append(arrayOfString[0].charAt(0));
				} catch (BadHanyuPinyinOutputFormatCombination localBadHanyuPinyinOutputFormatCombination)
				{
					localBadHanyuPinyinOutputFormatCombination.printStackTrace();
					localStringBuffer.append(arrayOfChar[i]);
				}
			}
		}
		return localStringBuffer.toString().replaceAll("\\W", "").trim();
	}

	public static String cn2Spell(String paramString)
	{
		StringBuffer localStringBuffer = new StringBuffer();
		char[] arrayOfChar = paramString.toCharArray();
		HanyuPinyinOutputFormat localHanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
		localHanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		localHanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		
		for (int i = 0; i < arrayOfChar.length; i++)
		{
			if (arrayOfChar[i] > 128)
			{
				try
				{
					localStringBuffer.append(PinyinHelper.toHanyuPinyinStringArray(arrayOfChar[i], localHanyuPinyinOutputFormat)[0]);
				} catch (BadHanyuPinyinOutputFormatCombination localBadHanyuPinyinOutputFormatCombination)
				{
					localBadHanyuPinyinOutputFormatCombination.printStackTrace();
					localStringBuffer.append(arrayOfChar[i]);
				}
			}
		}
		return localStringBuffer.toString();
	}
}
