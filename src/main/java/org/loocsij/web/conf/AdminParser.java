package org.loocsij.web.conf;
import org.w3c.dom.Node;

import org.loocsij.web.bean.AdminConfBean;
public final class AdminParser extends AppConfParser{
	public AdminParser(String xmlFile) {
		super(xmlFile);
	}
	public void transform(Node node) {
		String name = getChildValue(node,Node.ELEMENT_NODE,"name",0);
		String path = getChildValue(node,Node.ELEMENT_NODE,"path",1);	
		AdminConfBean adminWrapper = new AdminConfBean();
		adminWrapper.setName(name);
		adminWrapper.setPath(path);
		this.getContainer().add(adminWrapper);
	}
}
 