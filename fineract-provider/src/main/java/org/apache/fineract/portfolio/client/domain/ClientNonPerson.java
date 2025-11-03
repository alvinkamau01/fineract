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
package org.apache.fineract.portfolio.client.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.fineract.infrastructure.codes.domain.CodeValue;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;

@Entity
@Table(name = "m_client_non_person")
public class ClientNonPerson extends AbstractPersistableCustom<Long> {

    @OneToOne(optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false, unique = true)
    private Client client;

    @Column(name = "incorp_no", length = 50, nullable = true)
    private String incorpNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_business_line_cv_id", nullable = true)
    private CodeValue mainBusinessLine;

    @Column(name = "remarks", length = 150, nullable = true)
    private String remarks;


    public static ClientNonPerson createNew(final Client client, final CodeValue mainBusinessLine,
            String incorpNumber, String remarks) {
        return new ClientNonPerson(client, mainBusinessLine, incorpNumber, remarks);
    }

    protected ClientNonPerson() {
        //
    }

    private ClientNonPerson(final Client client, final CodeValue mainBusinessLine, final String incorpNumber,
            final String remarks) {
        if (client != null) {
            this.client = client;
        }

        if (mainBusinessLine != null) {
            this.mainBusinessLine = mainBusinessLine;
        }

        if (StringUtils.isNotBlank(incorpNumber)) {
            this.incorpNumber = incorpNumber.trim();
        }

        if (StringUtils.isNotBlank(remarks)) {
            this.remarks = remarks.trim();
        }

        validate(client);
    }

    private void validate(final Client client) {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();

        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException(dataValidationErrors);
        }

    }


    public Long mainBusinessLineId() {
        Long mainBusinessLineId = null;
        if (this.mainBusinessLine != null) {
            mainBusinessLineId = this.mainBusinessLine.getId();
        }
        return mainBusinessLineId;
    }

    public void updateMainBusinessLine(CodeValue mainBusinessLine) {
        this.mainBusinessLine = mainBusinessLine;
    }

    public Map<String, Object> update(final JsonCommand command) {

        final Map<String, Object> actualChanges = new LinkedHashMap<>(9);

        if (command.isChangeInStringParameterNamed(ClientApiConstants.incorpNumberParamName, this.incorpNumber)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.incorpNumberParamName);
            actualChanges.put(ClientApiConstants.incorpNumberParamName, newValue);
            this.incorpNumber = StringUtils.defaultIfEmpty(newValue, null);
        }

        if (command.isChangeInStringParameterNamed(ClientApiConstants.remarksParamName, this.remarks)) {
            final String newValue = command.stringValueOfParameterNamed(ClientApiConstants.remarksParamName);
            actualChanges.put(ClientApiConstants.remarksParamName, newValue);
            this.remarks = StringUtils.defaultIfEmpty(newValue, null);
        }


        if (command.isChangeInLongParameterNamed(ClientApiConstants.mainBusinessLineIdParamName, mainBusinessLineId())) {
            final Long newValue = command.longValueOfParameterNamed(ClientApiConstants.mainBusinessLineIdParamName);
            actualChanges.put(ClientApiConstants.mainBusinessLineIdParamName, newValue);
        }

        // validate();

        return actualChanges;

    }
}
