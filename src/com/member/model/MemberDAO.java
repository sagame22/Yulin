package com.member.model;

import java.util.List;

public interface MemberDAO {
	
	public int getTotal();
	public void add(MemberVO bean);
	public void update(MemberVO bean);
	public void delete(int id);
    public MemberVO get(int id);
    public List<MemberVO> list();
    public List<MemberVO> list(int start, int count);
    public MemberVO get(String name);
    public MemberVO get(String name, String password);
}
