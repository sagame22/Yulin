package com.member.model;

	public class MemberVO {
	    private String password;
	    private String name;
	    private int memberId;
	 
	    public int getMemberId() {
	        return memberId;
	    }
	 
	    public void setMemberId(int id) {
	        this.memberId = id;
	    }
	    public String getPassword() {
	        return password;
	    }
	    public void setPassword(String password) {
	        this.password = password;
	    }
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
	    public String getAnonymousName(){
	        if(null==name)
	            return null;
	         
	        if(name.length()<=1)
	            return "*";
	         
	        if(name.length()==2)
	            return name.substring(0,1) +"*";
	         
	        char[] cs =name.toCharArray();
	        for (int i = 1; i < cs.length-1; i++) {
	            cs[i]='*';
	        }
	        return new String(cs);
	         
	    }
	     
	}

