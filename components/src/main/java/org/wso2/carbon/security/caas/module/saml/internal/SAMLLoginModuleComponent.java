/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.security.caas.module.saml.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.security.caas.boot.ProxyLoginModule;
import org.wso2.carbon.security.caas.jaas.CarbonCallbackHandler;
import org.wso2.carbon.security.caas.jaas.util.CarbonSecurityConstants;
import org.wso2.carbon.security.caas.module.saml.SAML2LoginModule;
import org.wso2.carbon.security.caas.module.saml.internal.osgi.SAML2LoginModuleFactory;
import org.wso2.carbon.security.caas.module.saml.internal.osgi.SAMLCallbackHandlerFactory;

import java.util.Hashtable;
import javax.security.auth.spi.LoginModule;

/**
 * OSGi component for SAML Login Module.
 * @since 1.0.0
 */
@Component(
        name = "org.wso2.carbon.security.caas.module.saml.SAMLLoginModuleComponent",
        immediate = true
)
public class SAMLLoginModuleComponent {

    private static final Logger log = LoggerFactory.getLogger(SAMLLoginModuleComponent.class);

    /**
     * Register SAML LoginModule and Callback Handler as service factories.
     * @param bundleContext Bundle Context.
     */
    @Activate
    public void registerCarbonSecurityConnectors(BundleContext bundleContext) {

        Hashtable<String, String> samlLoginModuleProps = new Hashtable<>();
        samlLoginModuleProps.put(ProxyLoginModule.LOGIN_MODULE_SEARCH_KEY, SAML2LoginModule.class.getName());
        bundleContext.registerService(LoginModule.class, new SAML2LoginModuleFactory(), samlLoginModuleProps);

        Hashtable<String, String> samlCallbackHandlerProps = new Hashtable<>();
        samlCallbackHandlerProps.put(CarbonCallbackHandler.SUPPORTED_LOGIN_MODULE,
                                    CarbonSecurityConstants.SAML_LOGIN_MODULE);
        bundleContext.registerService(CarbonCallbackHandler.class, new SAMLCallbackHandlerFactory(),
                                      samlCallbackHandlerProps);
    }
}
