/*
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.vcloud.director.v1_5.domain;

import static com.google.common.base.Objects.equal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * Represents vApp re-composition parameters.
 * 
 * <pre>
 * &lt;complexType name="RecomposeVAppParams">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.vmware.com/vcloud/v1.5}ComposeVAppParamsType">
 *       &lt;sequence>
 *         &lt;element name="CreateItem" type="{http://www.vmware.com/vcloud/v1.5}VmType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DeleteItem" type="{http://www.vmware.com/vcloud/v1.5}ReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlRootElement(name = "RecomposeVAppParams")
@XmlType(name = "RecomposeVAppParamsType")
public class RecomposeVAppParams extends ComposeVAppParams {

   public static Builder builder() {
      return new Builder();
   }

   @Override
   public Builder toBuilder() {
      return new Builder().fromRecomposeVAppParams(this);
   }

   public static class Builder extends ComposeVAppParams.Builder {

      private List<Vm> createItem;
      private List<Reference> deleteItem;

      /**
       * @see RecomposeVAppParams#getCreateItem()
       */
      public Builder createItem(List<Vm> createItem) {
         this.createItem = createItem;
         return this;
      }

      /**
       * @see RecomposeVAppParams#getDeleteItem()
       */
      public Builder deleteItem(List<Reference> deleteItem) {
         this.deleteItem = deleteItem;
         return this;
      }

      @Override
      public RecomposeVAppParams build() {
         return new RecomposeVAppParams(description, name, vAppParent, instantiationParams, deploy, powerOn, sourcedItems, allEULAsAccepted, linkedClone, createItem, deleteItem);
      }

      @Override
      public Builder fromComposeVAppParams(ComposeVAppParams in) {
         return Builder.class.cast(super.fromComposeVAppParams(in));
      }

      public Builder fromRecomposeVAppParams(RecomposeVAppParams in) {
         return fromComposeVAppParams(in).createItem(in.getCreateItem()).deleteItem(in.getDeleteItem());
      }
   }

   private RecomposeVAppParams() {
      // For JAXB and builder use
   }

   private RecomposeVAppParams(String description, String name, Reference vAppParent, InstantiationParams instantiationParams, Boolean deploy, Boolean powerOn,
									    List<SourcedCompositionItemParam> sourcedItems, Boolean allEULAsAccepted, Boolean linkedClone, List<Vm> createItem, List<Reference> deleteItem) {
      super(description, name, vAppParent, instantiationParams, deploy, powerOn, sourcedItems, allEULAsAccepted, linkedClone);
      this.createItem = createItem;
      this.deleteItem = deleteItem;
   }

   @XmlElement(name = "CreateItem")
   protected List<Vm> createItem;
   @XmlElement(name = "DeleteItem")
   protected List<Reference> deleteItem;

   /**
    * Gets the value of the createItem property.
    */
   public List<Vm> getCreateItem() {
      if (createItem == null) {
         createItem = new ArrayList<Vm>();
      }
      return this.createItem;
   }

   /**
    * Gets the value of the deleteItem property.
    */
   public List<Reference> getDeleteItem() {
      if (deleteItem == null) {
         deleteItem = new ArrayList<Reference>();
      }
      return this.deleteItem;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      RecomposeVAppParams that = RecomposeVAppParams.class.cast(o);
      return super.equals(that) &&
            equal(this.createItem, that.createItem) && equal(this.deleteItem, that.deleteItem);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), createItem, deleteItem);
   }

   @Override
   public ToStringHelper string() {
      return super.string().add("createItem", createItem).add("deleteItem", deleteItem);
   }

}
