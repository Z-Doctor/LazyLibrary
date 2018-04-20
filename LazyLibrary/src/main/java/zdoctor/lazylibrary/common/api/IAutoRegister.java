package zdoctor.lazylibrary.common.api;

public interface IAutoRegister {
	
	public RegisterType getType();
	
	public int getSubCount();
	
	public static enum RegisterType {
		ITEM,
		BLOCK,
		ORE,
		TILEENTITY,
		ENTITY;
	}

	String getSubName(int meta);
}
