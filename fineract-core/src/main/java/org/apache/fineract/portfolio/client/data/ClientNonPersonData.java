/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.portfolio.client.data;

import java.io.Serializable;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;

/**
 * Immutable data object representing the ClientNonPerson
 */
@SuppressWarnings("unused")
public class ClientNonPersonData implements Serializable {

    private final String incorpNumber;
    private final CodeValueData mainBusinessLine;
    private final String remarks;
    private final Integer dailySales;
    private final Integer operatingCapital;
    private final Integer grossIncome;
    private final Integer expenses;
    private final Integer netIncome;

    // import fields
    private Long mainBusinessLineId;
    private String locale;
    private String dateFormat;

    public static ClientNonPersonData importInstance(String incorporationNo, String remarks, Long mainBusinessLineId, String locale,
            String dateFormat) {
        return new ClientNonPersonData(incorporationNo, remarks, mainBusinessLineId, locale, dateFormat);
    }

    private ClientNonPersonData(String incorpNumber, String remarks, Long mainBusinessLineId, String locale, String dateFormat) {

        this.incorpNumber = incorpNumber;
        this.remarks = remarks;
        this.mainBusinessLineId = mainBusinessLineId;
        this.dateFormat = dateFormat;
        this.locale = locale;
        this.mainBusinessLine = null;
        this.dailySales = null;
        this.operatingCapital = null;
        this.grossIncome = null;
        this.expenses = null;
        this.netIncome = null;
    }

    public ClientNonPersonData(String incorpNo, CodeValueData mainBusinessLine, String remarks, Integer dailySales,
            Integer operatingCapital, Integer grossIncome, Integer expenses, Integer netIncome) {

        this.incorpNumber = incorpNo;
        this.mainBusinessLine = mainBusinessLine;
        this.remarks = remarks;
        this.dailySales = dailySales;
        this.operatingCapital = operatingCapital;
        this.grossIncome = grossIncome;
        this.expenses = expenses;
        this.netIncome = netIncome;
    }

}
