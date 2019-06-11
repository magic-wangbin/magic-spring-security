/**
 * 
 */
package com.magic.security.rbac.repository.spec;

import com.magic.security.rbac.domain.Admin;
import com.magic.security.rbac.dto.AdminCondition;
import com.magic.security.rbac.repository.support.ImoocSpecification;
import com.magic.security.rbac.repository.support.QueryWraper;

/**
 * @author zhailiang
 *
 */
public class AdminSpec extends ImoocSpecification<Admin, AdminCondition> {

	public AdminSpec(AdminCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Admin> queryWraper) {
		addLikeCondition(queryWraper, "username");
	}

}
