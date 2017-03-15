/*
 * Copyright 2016-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.drivers.corsa;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.net.driver.AbstractDriverLoader;
import org.onosproject.net.driver.DefaultDriverProviderService;

/**
 * Loader for Corsa device drivers.
 */
@Component(immediate = true)
public class CorsaDriversLoader extends AbstractDriverLoader {

    //This reference is needed to enforce loading of the default drivers before
    //the corsa drivers at ONOS startup if corsa.drivers is specified
    //in the ONOS_APPS variable.
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    private DefaultDriverProviderService defaultDriverProviderService;

    public CorsaDriversLoader() {
        super("/corsa-drivers.xml");
    }
}
