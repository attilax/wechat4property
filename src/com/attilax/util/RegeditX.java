package com.attilax.util;

 
	
    import java.util.Iterator;  
    import ca.beq.util.win32.registry.RegistryException;  
    import ca.beq.util.win32.registry.RegistryKey;  
    import ca.beq.util.win32.registry.RegistryValue;  
    import ca.beq.util.win32.registry.RootKey;  
    import ca.beq.util.win32.registry.ValueType;  
    public class RegeditX {  
        /** 
         * @param args 
         */  
        public static void main(String[] args) {  
          //  tt();
        	//HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Session Manager\PendingFileRenameOperations
            RegistryKey root = new RegistryKey(RootKey.HKEY_CURRENT_USER,  
                    "Software");  
        }

		private static void tt() {
			String keyName = "Test";  
            // 得到或创建新root key  
            RegistryKey root = new RegistryKey(RootKey.HKEY_CURRENT_USER,  
                    "Software");  
            if (!root.exists()) {  
                root.create();  
            }  
            // 创建并获取子键  
            RegistryKey subKey = null;  
            if (!root.hasSubkey(keyName)) {  
                subKey = root.createSubkey(keyName);  
            }  
            subKey = new RegistryKey(RootKey.HKEY_CURRENT_USER, "Software" + "//"  
                    + keyName);  
            // 设置注册表中键的值  
            RegistryValue v = new RegistryValue("myVal", ValueType.REG_SZ, "data");  
            subKey.setValue(v);  
            // 读注册表中键的值  
            if (subKey.hasValue("myVal")) {  
                RegistryValue v1 = subKey.getValue("myVal");  
                System.out.println(v1.toString());//     
                System.out.println(v1.getData().toString());//     
            } // if  
            // 注：v.toString()仅是键myValue对应的键值，若要得到myValue键对应的值数据，  
            // 则需要String str =v.getData().toSting();  
            // 枚举子键  
            if (subKey.hasSubkeys()) {  
                Iterator<?> i = subKey.subkeys();  
                while (i.hasNext()) {  
                    RegistryKey x = (RegistryKey) i.next();  
                    System.out.println(x.toString());  
                } // while  
            } // if  
            // 删除一个已存在的键值：  
            try {  
                subKey.delete();  
            } // try  
            catch (RegistryException re) {  
                re.printStackTrace();  
            } // catch  
		}  
    }  

 
