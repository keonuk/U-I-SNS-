package friend.db;

public class FriendBean {
	
	// ȸ�� ���̺� (semi_member)
	private String m_Id;			// ��� ���̵�
	private String m_pass;			// ��� ��й�ȣ
	private String m_name;			// ��� �̸�
	private String m_birth;			// ��� ����
	private String m_gender;		// ��� ����
	private String m_email;			// ��� �̸���
	private String m_phoneNum;		// ��� ��ȭ��ȣ
	private String m_PhotoName;		// ��� ������ ���� �̸�
	
	// ģ�� ��� ���̺�(semi_member_list)
	private String my_id;			// ����� ���̵�
	private String fr_id;			// ����ڰ� �߰��� ģ�� ���̵�
	private String fr_name;			// ģ�� �̸�
	private String fr_birth;		// ģ�� ����
	private String fr_gender;		// ģ�� ����
	private String fr_email;		// ģ�� �̸���
	private String fr_phoneNum;		// ģ�� ��ȭ��ȣ
	private String fr_PhotoName;	// ģ�� ������ ���� �̸�
	
	// �̰����� ������ ���콺 ��ư Ŭ�� -> Source -> Generate Getters and Setters
	public String getM_Id() {
		return m_Id;
	}
	public void setM_Id(String m_Id) {
		this.m_Id = m_Id;
	}
	public String getM_pass() {
		return m_pass;
	}
	public void setM_pass(String m_pass) {
		this.m_pass = m_pass;
	}
	public String getM_name() {
		return m_name;
	}
	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	public String getM_birth() {
		return m_birth;
	}
	public void setM_birth(String m_birth) {
		this.m_birth = m_birth;
	}
	public String getM_gender() {
		return m_gender;
	}
	public void setM_gender(String m_gender) {
		this.m_gender = m_gender;
	}
	public String getM_email() {
		return m_email;
	}
	public void setM_email(String m_email) {
		this.m_email = m_email;
	}
	public String getM_phoneNum() {
		return m_phoneNum;
	}
	public void setM_phoneNum(String m_phoneNum) {
		this.m_phoneNum = m_phoneNum;
	}
	public String getM_PhotoName() {
		return m_PhotoName;
	}
	public void setM_PhotoName(String m_PhotoName) {
		this.m_PhotoName = m_PhotoName;
	}
	public String getMy_id() {
		return my_id;
	}
	public void setMy_id(String my_id) {
		this.my_id = my_id;
	}
	public String getFr_id() {
		return fr_id;
	}
	public void setFr_id(String fr_id) {
		this.fr_id = fr_id;
	}
	public String getFr_name() {
		return fr_name;
	}
	public void setFr_name(String fr_name) {
		this.fr_name = fr_name;
	}
	public String getFr_PhotoName() {
		return fr_PhotoName;
	}
	public void setFr_PhotoName(String fr_PhotoName) {
		this.fr_PhotoName = fr_PhotoName;
	}
	public String getFr_birth() {
		return fr_birth;
	}
	public void setFr_birth(String fr_birth) {
		this.fr_birth = fr_birth;
	}
	public String getFr_gender() {
		return fr_gender;
	}
	public void setFr_gender(String fr_gender) {
		this.fr_gender = fr_gender;
	}
	public String getFr_email() {
		return fr_email;
	}
	public void setFr_email(String fr_email) {
		this.fr_email = fr_email;
	}
	public String getFr_phoneNum() {
		return fr_phoneNum;
	}
	public void setFr_phoneNum(String fr_phoneNum) {
		this.fr_phoneNum = fr_phoneNum;
	}
	
}