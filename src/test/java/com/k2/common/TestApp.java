package com.k2.common;

import com.k2.app.K2;
import com.k2.app.K2Config;
import com.k2.common.annotation.K2Application;
import com.k2.common.annotation.Version;
import com.k2.core.K2CoreDomainManager;

@K2Application(
		name = "Test Application",
		description = "This is a test application",
		domainManagers = {
				K2CoreDomainManager.class
		},
		version = @Version(major = 1, minor = 2, point = 3, build = 4)
		)
public class TestApp {}
