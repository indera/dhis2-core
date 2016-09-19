package org.hisp.dhis.dataset;

/*
 * Copyright (c) 2004-2016, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.hisp.dhis.common.BaseIdentifiableObject;
import org.hisp.dhis.common.DxfNamespaces;
import org.hisp.dhis.common.IdentifiableObject;
import org.hisp.dhis.common.MergeMode;
import org.hisp.dhis.dataelement.DataElement;
import org.hisp.dhis.dataelement.DataElementCategoryCombo;

/**
 * @author Lars Helge Overland
 */
public class DataSetElement
    extends BaseIdentifiableObject
{
    private DataSet dataSet;

    private DataElement dataElement;

    private DataElementCategoryCombo categoryCombo;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public DataSetElement()
    {
        setAutoFields();
    }

    public DataSetElement( DataSet dataSet, DataElement dataElement, DataElementCategoryCombo categoryCombo )
    {
        setAutoFields();
        this.dataSet = dataSet;
        this.dataElement = dataElement;
        this.categoryCombo = categoryCombo;
    }

    // -------------------------------------------------------------------------
    // Logic
    // -------------------------------------------------------------------------

    public DataElementCategoryCombo getCategoryComboFallback()
    {
        return hasCategoryCombo() ? getCategoryCombo() : dataElement.getCategoryCombo();
    }

    public boolean hasCategoryCombo()
    {
        return categoryCombo != null;
    }

    public boolean hasCategoryComboFallback()
    {
        return hasCategoryCombo() || dataElement.hasCategoryCombo();
    }

    // -------------------------------------------------------------------------
    // Get and set methods
    // -------------------------------------------------------------------------

    @JsonProperty
    @JsonSerialize( as = BaseIdentifiableObject.class )
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public DataSet getDataSet()
    {
        return dataSet;
    }

    public void setDataSet( DataSet dataSet )
    {
        this.dataSet = dataSet;
    }

    @JsonProperty
    @JsonSerialize( as = BaseIdentifiableObject.class )
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public DataElement getDataElement()
    {
        return dataElement;
    }

    public void setDataElement( DataElement dataElement )
    {
        this.dataElement = dataElement;
    }

    @JsonProperty
    @JsonSerialize( as = BaseIdentifiableObject.class )
    @JacksonXmlProperty( namespace = DxfNamespaces.DXF_2_0 )
    public DataElementCategoryCombo getCategoryCombo()
    {
        return categoryCombo;
    }

    public void setCategoryCombo( DataElementCategoryCombo categoryCombo )
    {
        this.categoryCombo = categoryCombo;
    }

    @Override
    public void mergeWith( IdentifiableObject other, MergeMode mergeMode )
    {
        super.mergeWith( other, mergeMode );

        if ( other.getClass().isInstance( this ) )
        {
            DataSetElement dataSetElement = (DataSetElement) other;

            if ( mergeMode.isReplace() )
            {
                categoryCombo = dataSetElement.getCategoryCombo();
                dataElement = dataSetElement.getDataElement();
                dataSet = dataSetElement.getDataSet();
            }
            else if ( mergeMode.isMerge() )
            {
                categoryCombo = dataSetElement.getCategoryCombo() == null ? categoryCombo : dataSetElement.getCategoryCombo();
                dataElement = dataSetElement.getDataElement() == null ? dataElement : dataSetElement.getDataElement();
                dataSet = dataSetElement.getDataSet() == null ? dataSet : dataSetElement.getDataSet();
            }
        }
    }
}
