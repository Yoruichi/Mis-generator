package ${basePackage}.model;

import java.io.Serializable;

import me.yoruichi.mis.BasePo;
<#list importTypes as importType>
${importType}
</#list>
/**
 * 
 * ${sqlComment}
 *
 */
public class ${className} extends BasePo {
	
	<#list dataModelColumns as column>
	private ${column.javaType} ${column.fieldName};//${column.sqlComment}
	</#list>

<@generateSetterGetter/>

}

<#-- 定义宏，生成属性的getter和setter方法 -->
<#macro generateSetterGetter>
	<#list dataModelColumns as column>
		
		<#if column.javaType?index_of("String") = 0><#-- 如果是字符串类型的属性，在setter的时候进行trim -->
	public void set${column.operName}(${column.javaType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName} == null ? null : ${column.fieldName}.trim();
	}
		<#else>
	public void set${column.operName}(${column.javaType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
	}
		</#if>
	public ${column.javaType} get${column.operName}() {
		return this.${column.fieldName};
	}
	</#list>
</#macro>

<#-- 定义宏，生成属性的getter方法 -->
<#macro generateGetter>
	<#list dataModelColumns as column>
	public ${column.javaType} get${column.operName}() {
		return this.${column.fieldName};
	}
	</#list>
</#macro>

<#-- 定义宏，生成属性的setter方法 -->
<#macro generateSetter>
	<#list dataModelColumns as column>
		
		<#if column.javaType?index_of("String") = 0><#-- 如果是字符串类型的属性，在setter的时候进行trim -->
	public void set${column.operName}(${column.javaType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName} == null ? null : ${column.fieldName}.trim();
	}
		<#else>
	public void set${column.operName}(${column.javaType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
	}
		</#if>
	</#list>
</#macro>


