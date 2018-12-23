package hao.webapp.demo;

public class Search {

	static int size = 100000000;
	//赋值
	static int[] array = new int[(size-2)];
	
	private static void isExsit(int low,int up) {
		int lowNum = array[low];
		int upNum  = array[up];
		if(upNum-lowNum != up-low) {
			//计算low是
			if(low>0) {
				if(array[low]-array[low-1]>1) {
					for(int i=1;i<(array[low]-array[low-1]);i++) {
						System.out.println(array[low-1]+i);
					}
				}
			}else {
				if(array[low]!=1) {
					for(int i=1;i<(array[low]-1);i++) {
						System.out.println(1+i);
					}
				}
			}
			if(up<(array.length-1)) {
				if(array[up+1]-array[up]>1) {
					for(int i=1;i<(array[up+1]-array[up]);i++) {
						System.out.println(array[up]+i);
					}
				}
			}else {
				if(array[up]!=size) {
					for(int i=1;i<(size-array[up]);i++) {
						System.out.println(array[up]+i);
					}
				}
			}
			if((up-low)<3) {
				int zj = low+1;
				if(array[up]-array[zj]>1) {
					for(int i=1;i<(array[up]-array[zj]);i++) {
						System.out.println(array[zj]+i);
					}
				}
				if(array[zj]-array[low]>1) {
					for(int i=1;i<(array[zj]-array[low]);i++) {
						System.out.println(array[low]+i);
					}
				}
			}
			
			int mid = (low+up)/2;
			if(low<mid&&mid<up) {
				isExsit(low,mid);
				isExsit(mid,up);
			}
		}
	}
	
	
	private static void isExsit2(int low,int up) {
		int offset = 0;
		while(offset<=(up-low)/2) {
			//这里有异常情况
			if((low+offset)>0) {
				if(array[low+offset]-array[low+offset-1]>1) {
					for(int i=1;i<(array[low+offset]-array[low+offset-1]);i++) {
						System.out.println(array[low+offset-1]+i);
					}
				}
			}
			if((up-offset)<(array.length-1)) {
				if(array[up-offset+1]-array[up-offset]>1) {
					for(int i=1;i<(array[up-offset+1]-array[up-offset]);i++) {
						System.out.println(array[up-offset]+i);
					}
				}
			}
			offset++;
		}
	}
	
	public static void main(String[] args) {
		int index = 0;//数组下标
		for(int i=1;i<=size;i++) {
			if(!(i==4||i==7)) {
				array[index] = i;
				index++;
			}
		}
		//二分查找
		System.out.println("开始时间================>"+System.currentTimeMillis());
		for(int i=1;i<=size;i++) {
			//二分发查找
			int low = 0;//下限
			int up  = array.length-1;
			int mid = (low+up)/2;
			boolean exsit = false;
			while(!(low>=mid||up<=mid)) {
				if(array[low]<i&&i<array[mid]) {
					up=mid;
					mid = (up+low)/2;
				}else if(array[mid]<i&&i<array[up]) {
					low = mid;
					mid = (up+low)/2;
				}else {
					exsit = true;
					break;
				}
			}
			low = 0;//下限
			up  = array.length-1;
			if(!exsit) {
				System.out.println("不存在=>"+i);
			}
		}		
		System.out.println("结束时间================>"+System.currentTimeMillis());
		
		
		
		System.out.println("开始时间================>"+System.currentTimeMillis());
		isExsit2(0,array.length-1);
		System.out.println("结束时间================>"+System.currentTimeMillis());
		
		
		//递归来做
		System.out.println("开始时间================>"+System.currentTimeMillis());
		isExsit(0,array.length-1);
		System.out.println("结束时间================>"+System.currentTimeMillis());
	}
	
}
