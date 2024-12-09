package com.normalworks.common.companieshouse.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CompanyLinks
 *
 * @author: lingeng
 * @date: 27/11/2023
 */
public class CompanyLinks {

    @JSONField(name = "persons_with_significant_control")
    private String personsWithSignificantControl;
    @JSONField(name = "persons_with_significant_control_statements")
    private String personsWithSignificantControlStatements;
    private String registers;
    private String self;

    public String getPersonsWithSignificantControl() {
        return personsWithSignificantControl;
    }

    public void setPersonsWithSignificantControl(String personsWithSignificantControl) {
        this.personsWithSignificantControl = personsWithSignificantControl;
    }

    public String getPersonsWithSignificantControlStatements() {
        return personsWithSignificantControlStatements;
    }

    public void setPersonsWithSignificantControlStatements(String personsWithSignificantControlStatements) {
        this.personsWithSignificantControlStatements = personsWithSignificantControlStatements;
    }

    public String getRegisters() {
        return registers;
    }

    public void setRegisters(String registers) {
        this.registers = registers;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
