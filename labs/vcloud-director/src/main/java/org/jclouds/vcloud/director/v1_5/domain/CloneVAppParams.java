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

import javax.xml.bind.annotation.XmlType;

/**
 * Represents parameters for copying a vApp and optionally deleting the source.
 * 
 * <pre>
 * &lt;complexType name="CloneVAppParams" /&gt;
 * </pre>
 * 
 * @author grkvlt@apache.org
 */
@XmlType(name = "CloneVAppParams")
public class CloneVAppParams extends InstantiateVAppParamsType<CloneVAppParams> {

   @SuppressWarnings("unchecked")
   public static Builder builder() {
      return new Builder();
   }

   @Override
   public Builder toBuilder() {
      return new Builder().fromCloneVAppParams(this);
   }

   public static class Builder extends InstantiateVAppParamsType.Builder<CloneVAppParams> {

      @Override
      public CloneVAppParams build() {
         return new CloneVAppParams(description, name, vAppParent, instantiationParams, deploy, powerOn, source, sourceDelete, linkedClone);
      }

      /**
       * @see InstantiateVAppParamsType#getSource()
       */
      @Override
      public Builder source(ReferenceType<?> source) {
         this.source = source;
         return this;
      }

      /**
       * @see InstantiateVAppParamsType#isSourceDelete()
       */
      @Override
      public Builder isSourceDelete(Boolean sourceDelete) {
         this.sourceDelete = sourceDelete;
         return this;
      }

      /**
       * @see InstantiateVAppParamsType#isSourceDelete()
       */
      @Override
      public Builder sourceDelete() {
         this.sourceDelete = Boolean.TRUE;
         return this;
      }

      /**
       * @see InstantiateVAppParamsType#isSourceDelete()
       */
      @Override
      public Builder notSourceDelete() {
         this.sourceDelete = Boolean.FALSE;
         return this;
      }

      /**
       * @see InstantiateVAppParamsType#isLinkedClone()
       */
      @Override
      public Builder isLinkedClone(Boolean linkedClone) {
         this.linkedClone = linkedClone;
         return this;
      }

      /**
       * @see InstantiateVAppParamsType#isLinkedClone()
       */
      @Override
      public Builder linkedClone() {
         this.linkedClone = Boolean.TRUE;
         return this;
      }

      /**
       * @see InstantiateVAppParamsType#isLinkedClone()
       */
      @Override
      public Builder notLinkedClone() {
         this.linkedClone = Boolean.FALSE;
         return this;
      }

      /**
       * @see ParamsType#getDescription()
       */
      @Override
      public Builder description(String description) {
         super.description(description);
         return this;
      }

      /**
       * @see ParamsType#getName()
       */
      @Override
      public Builder name(String name) {
         super.name(name);
         return this;
      }

      /**
       * @see VAppCreationParamsType#getVAppParent()
       */
      @Override
      public Builder vAppParent(Reference vAppParent) {
         this.vAppParent = vAppParent;
         return this;
      }

      /**
       * @see VAppCreationParamsType#getInstantiationParams()
       */
      @Override
      public Builder instantiationParams(InstantiationParams instantiationParams) {
         this.instantiationParams = instantiationParams;
         return this;
      }

      /**
       * @see VAppCreationParamsType#isDeploy()
       */
      @Override
      public Builder deploy(Boolean deploy) {
         this.deploy = deploy;
         return this;
      }

      /**
       * @see VAppCreationParamsType#isDeploy()
       */
      @Override
      public Builder deploy() {
         this.deploy = Boolean.TRUE;
         return this;
      }

      /**
       * @see VAppCreationParamsType#isDeploy()
       */
      @Override
      public Builder notDeploy() {
         this.deploy = Boolean.FALSE;
         return this;
      }

      /**
       * @see VAppCreationParamsType#isPowerOn()
       */
      @Override
      public Builder powerOn(Boolean powerOn) {
         this.powerOn = powerOn;
         return this;
      }

      /**
       * @see VAppCreationParamsType#isPowerOn()
       */
      @Override
      public Builder powerOn() {
         this.powerOn = Boolean.TRUE;
         return this;
      }

      /**
       * @see VAppCreationParamsType#isPowerOn()
       */
      @Override
      public Builder notPowerOn() {
         this.powerOn = Boolean.FALSE;
         return this;
      }

      @Override
      public Builder fromInstantiateVAppParamsType(InstantiateVAppParamsType<CloneVAppParams> in) {
         return Builder.class.cast(super.fromInstantiateVAppParamsType(in));
      }

      public Builder fromCloneVAppParams(CloneVAppParams in) {
         return fromInstantiateVAppParamsType(in);
      }
   }

   protected CloneVAppParams() {
      // For JAXB and builder use
   }

   public CloneVAppParams(String description, String name, Reference vAppParent, InstantiationParams instantiationParams,
						         Boolean deploy, Boolean powerOn, ReferenceType<?> source, Boolean sourceDelete, Boolean linkedClone) {
      super(description, name, vAppParent, instantiationParams, deploy, powerOn, source, sourceDelete, linkedClone);
   }
}
