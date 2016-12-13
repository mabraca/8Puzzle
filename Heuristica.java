public class Heuristica {
	public int getHeuristica1(int[] data, int[] sequence){
		int h1 = 0;
		for (int i=0; i<9; i++){
    		if (getBlank(data) != i){
	    		if (data[i] == sequence[i])
	    			h1 = h1 + 1;
    		}    		
    	}
		return h1;
	}
	public int getHeuristica2(int[] data, int[] sequence){
		int h2 = 0;
		for (int i=0; i<9; i++){
    		if (getBlank(data) != i){
	    		if (data[i] != sequence[i])
	    			h2 = h2 + 1;
    		}    		
    	}
		return h2;
	}
	public int getManhattan(int[] data, int[] sequence){
		int hm = 0;
		int[][] M = new int [3][3];
    	int[][] N = new int [3][3];
    	int k=0;
    	for (int i=0;i<3;i++){
    		for (int j=0;j<3;j++){
    			M[i][j] = sequence[k];
    			k=k+1;
    		}
    	}	
    	k=0;
    	for (int i=0;i<3;i++){
    		for (int j=0;j<3;j++){
    			N[i][j] = data[k];
    			k=k+1;
    		}
    	}
    	for (int i=0;i<3;i++){
    		for (int j=0;j<3;j++){
    	    	for (int ii=0;ii<3;ii++){
    	    		for (int jj=0;jj<3;jj++){
    	    			if (M[i][j] == N[ii][jj] && M[i][j] != 0){
    	    				hm = hm + Math.abs(ii-i)+ Math.abs(jj-j);
    	    			}
    	    		}
    	    	}
    		}
    	}
		return hm;
	}
	
	private int getBlank(int[] data){
		int blank = 0;
	    // Find the blank space
		for (int i=0; i<9; i++){
	    	if (data[i] == 0){
	    		blank = i;
	    	}
	    }	
		return blank;
	}
	
	// Best heuristica
	public int bestHeuristica(int[] ids, int node[][]){
		int pos=0;
		int h_min = 100;
		for (int i =0; i< ids.length; i++){
			int data[] = {0,0,0,0,0,0,0,0,0};
			int sequence[] = {1,2,3,8,0,4,7,6,5};
			if (ids[i] != -1)
			{
				System.arraycopy( node[ids[i]], 0, data, 0, data.length );
				int h = getManhattan(data, sequence);
				if (h>30){
					int a=0;
					a=a+1;
				}
					
				if (h<h_min){
					h_min = h;
					pos = ids[i];
				}
			}
							
		}
		return pos;
	}
}