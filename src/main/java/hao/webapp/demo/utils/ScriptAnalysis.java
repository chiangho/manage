package hao.webapp.demo.utils;

import java.util.HashSet;
import java.util.Set;

public class ScriptAnalysis {

	Set<String> set = new HashSet<String>();

	public int ch_index;// 索引
	public int ch; // 字符
	public int code;// 状态码
	public int[] co_long = new int[] { 35, 36, 37, 38, 39, 40, 41, 42, 43, 21 };// 非单一字符的编码
	public String[] nosignal_Word = new String[] { ">", "<", ">=", "<=", "==", "!=", "&", "&&", "||", "=" };// 包含非单个的字符
	public StringBuffer strToken = new StringBuffer();// 存放构成单词符号的字符串
	public int[] co = new int[] { 1, 2, 3, 4, 5, 6, 7 };// 保留字的编码
	public String[] retainWord = new String[] { "main", "int", "char", "if", "else", "for", "while" };// 保留字

	// 判断是否是字母
	public boolean IsLetter() {
		if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || ch == 35) {
			return true;
		}
		return false;
	}

	// 判断是否时非字母字符 比如汉字等等
	public boolean IsNonAlphaChar() {
		if (ch >= 128) {
			return true;
		}
		return false;
	}

	// 判断是否是数字
	public boolean IsDigit() {
		if (ch >= 48 && ch <= 57) {
			return true;
		}
		return false;
	}

	// 判断是否是空格
	public boolean IsBC(int ch) {
		if (ch == 32) {
			return true;
		}
		return false;
	}

	// 判断是否含有非单字符
	public boolean IsNN(int ch) {
		if (ch == 33 || ch == 60 || ch == 62 || ch == 61 || ch == 38 || ch == 124) {
			return true;
		}
		return false;
	}

	// 连接字符
	public void Concat(char ch) {
		if (ch >= 128) {
			strToken.append(new String(new char[] { ch }));
		} else {
			strToken.append(ch);
		}
	}

	// 判断是否是保留字或者包含非单一字符
	public int keyword() {
		for (int i = 0; i < retainWord.length; i++) {
			if (strToken.toString().equals(retainWord[i])) {
				ch_index = i;
				return 1;
			}
			if (strToken.length() != 0) {
				if (strToken.charAt(0) >= '0' && strToken.charAt(0) <= '9') {
					return 3;
				}
			}
			if (strToken.length() == 0) {
				return 4;
			}

		}
		for (int i = 0; i < nosignal_Word.length; i++) {
			if (strToken.toString().equals(nosignal_Word[i])) {
				ch_index = i;
				return 5;
			}
		}
		return 2;
	}

	public void Retract() {
		// code = keyword();
		// if (code == 1) {
		// System.out.println("(" + co[ch_index] + "," + strToken + ")");// 保留字
		// } else if (code == 2) {
		// System.out.println("(" + 10 + "," + strToken + ")");// 标识符
		// } else if (code == 3) {
		// System.out.println("(" + 20 + "," + strToken + ")");// 整数
		// } else if (code == 5) {
		// System.out.println("(" + co_long[ch_index] + "," + strToken + ")");// 包含非单一字符
		// }
		set.add(strToken.toString());
		strToken.delete(0, strToken.length());
	}

	/**
	 * 读取文件并进行词法分析
	 */
	public void run(String script) {
		// BufferedReader br;
		// br = new BufferedReader(new
		// FileReader("C:\\Users\\chianghao\\Desktop\\tests.txt"));// 读取文件
		char[] chs = script.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			// while ((ch = br.read()) != -1) {// 每次读取一个字幕直到读取完
			ch = chs[i];
			if (IsBC(ch) == false) {// 判断是否为空格
				if (IsLetter()) {// 判断是否为字母
					Concat((char) ch);
				} else if (IsNonAlphaChar()) {
					Concat((char) ch);
				} else if (IsDigit()) {// 判断是否为数字
					Concat((char) ch);
				} else if (IsNN(ch)) {// 判断是否为包含非单一字符
					Concat((char) ch);
				} else if ((char) ch == '+') {
					Retract();
					// System.out.println("(" + 22 + "," + (char) ch + ")");
				} else if ((char) ch == '*') {
					Retract();
					// System.out.println("(" + 24 + "," + (char) ch + ")");
				} else if ((char) ch == '-') {
					Retract();
					// System.out.println("(" + 23 + "," + (char) ch + ")");
				} else if ((char) ch == '(') {
					Retract();
					// System.out.println("(" + 26 + "," + (char) ch + ")");
				} else if ((char) ch == ')') {
					Retract();
					// System.out.println("(" + 27 + "," + (char) ch + ")");
				} else if ((char) ch == '[') {
					Retract();
					// System.out.println("(" + 28 + "," + (char) ch + ")");
				} else if ((char) ch == ']') {
					Retract();
					System.out.println("(" + 29 + "," + (char) ch + ")");
				} else if ((char) ch == '{') {
					Retract();
					// System.out.println("(" + 30 + "," + (char) ch + ")");
				} else if ((char) ch == '}') {
					Retract();
					// System.out.println("(" + 31 + "," + (char) ch + ")");
				} else if ((char) ch == ',') {
					Retract();
					// System.out.println("(" + 32 + "," + (char) ch + ")");
				} else if ((char) ch == ':') {
					Retract();
					// System.out.println("(" + 33 + "," + (char) ch + ")");
				} else if ((char) ch == ';') {
					Retract();
					// System.out.println("(" + 34 + "," + (char) ch + ")");
				}
			} else {
				Retract();
			}
		}

	}
	/**
	 * 获取基础计算项目
	 * @return
	 */
	public Set<String> getBasicComputingItem(){
		Set<String> items = new HashSet<String>();
		for(String s:set) {
			if(s.startsWith("#")&&s.endsWith("#")) {
				items.add(s);
			}
		}
		return items;
	}
	
	
}
