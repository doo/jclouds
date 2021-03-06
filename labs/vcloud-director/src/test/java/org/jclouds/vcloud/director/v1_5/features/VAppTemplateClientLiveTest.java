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
package org.jclouds.vcloud.director.v1_5.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.net.URI;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import org.jclouds.vcloud.director.v1_5.domain.Checks;
import org.jclouds.vcloud.director.v1_5.domain.CustomizationSection;
import org.jclouds.vcloud.director.v1_5.domain.GuestCustomizationSection;
import org.jclouds.vcloud.director.v1_5.domain.LeaseSettingsSection;
import org.jclouds.vcloud.director.v1_5.domain.Metadata;
import org.jclouds.vcloud.director.v1_5.domain.MetadataEntry;
import org.jclouds.vcloud.director.v1_5.domain.MetadataValue;
import org.jclouds.vcloud.director.v1_5.domain.NetworkConfigSection;
import org.jclouds.vcloud.director.v1_5.domain.NetworkConfiguration;
import org.jclouds.vcloud.director.v1_5.domain.NetworkConnectionSection;
import org.jclouds.vcloud.director.v1_5.domain.Owner;
import org.jclouds.vcloud.director.v1_5.domain.ProductSectionList;
import org.jclouds.vcloud.director.v1_5.domain.Reference;
import org.jclouds.vcloud.director.v1_5.domain.RelocateParams;
import org.jclouds.vcloud.director.v1_5.domain.Task;
import org.jclouds.vcloud.director.v1_5.domain.VAppNetworkConfiguration;
import org.jclouds.vcloud.director.v1_5.domain.VAppTemplate;
import org.jclouds.vcloud.director.v1_5.domain.ovf.Envelope;
import org.jclouds.vcloud.director.v1_5.domain.ovf.NetworkSection;
import org.jclouds.vcloud.director.v1_5.internal.BaseVCloudDirectorClientLiveTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * Tests the request/response behavior of {@link org.jclouds.vcloud.director.v1_5.features.VAppTemplateClient}
 * 
 * NOTE The environment MUST have at least one template configured
 *
 * @author Aled Sage
 */
@Test(groups = {"live", "unit", "user"}, testName = "VAppTemplateClientLiveTest")
public class VAppTemplateClientLiveTest extends BaseVCloudDirectorClientLiveTest {

   private final Random random = new Random();
   private VAppTemplateClient vappTemplateClient;
   
   @BeforeClass(inheritGroups = true)
   @Override
   public void setupRequiredClients() {
      try {
         vappTemplateClient = context.getApi().getVAppTemplateClient();
      } catch (Exception e) {
         // TODO Declare super as throws Exception?
         throw Throwables.propagate(e);
      }
   }

   @Test
   public void testGetVAppTemplate() {
      VAppTemplate template = vappTemplateClient.getVAppTemplate(vAppTemplateURI);
      
      Checks.checkVAppTemplate(template);
      assertEquals(template.getHref(), vAppTemplateURI);
   }
   
   @Test
   public void testGetVAppTemplateOwner() {
      Owner owner = vappTemplateClient.getOwnerOfVAppTemplate(vAppTemplateURI);
      
      Checks.checkOwner(owner);
      assertEquals(owner.getUser(), vappTemplateClient.getVAppTemplate(vAppTemplateURI).getOwner().getUser());
   }
   
   @Test
   public void testGetVAppTemplateCustomizationSection() {
      CustomizationSection customizationSection = vappTemplateClient.getVAppTemplateCustomizationSection(vAppTemplateURI);
      
      Checks.checkCustomizationSection(customizationSection);
   }
   
   @Test
   public void testGetProductSectionsForVAppTemplate() {
      ProductSectionList productSectionList = vappTemplateClient.getProductSectionsForVAppTemplate(vAppTemplateURI);
      
      Checks.checkProductSectionList(productSectionList);
   }
   
   @Test
   public void testGetVAppTemplateGuestCustomizationSection() {
      GuestCustomizationSection guestCustomizationSection = vappTemplateClient.getVAppTemplateGuestCustomizationSection(vAppTemplateURI);
      
      Checks.checkGuestCustomizationSection(guestCustomizationSection);
   }
   
   @Test
   public void testGetVAppTemplateLeaseSettingsSection() {
      // FIXME Wrong case for Vapp
      LeaseSettingsSection leaseSettingsSection = vappTemplateClient.getVappTemplateLeaseSettingsSection(vAppTemplateURI);
      
      Checks.checkLeaseSettingsSection(leaseSettingsSection);
   }
   
   @Test
   public void testGetVAppTemplateMetadata() {
      Metadata metadata = vappTemplateClient.getVAppTemplateMetadata(vAppTemplateURI);
      
      Checks.checkMetadata(metadata);
   }

   @Test(enabled=false) // implicitly tested by testEditVAppTemplateMetadataValue, which first creates the metadata entry; otherwise no entry may exist
   public void testGetVAppTemplateMetadataValue() {
      Metadata metadata = vappTemplateClient.getVAppTemplateMetadata(vAppTemplateURI);
      MetadataEntry entry = Iterables.get(metadata.getMetadataEntries(), 0);
      
      MetadataValue val = vappTemplateClient.getVAppTemplateMetadataValue(vAppTemplateURI, entry.getKey());
      
      Checks.checkMetadataValue(val);
      assertEquals(val.getValue(), entry.getValue());
   }
   
   @Test
   public void testGetVAppTemplateNetworkConfigSection() {
      NetworkConfigSection networkConfigSection = vappTemplateClient.getVAppTemplateNetworkConfigSection(vAppTemplateURI);
      
      Checks.checkNetworkConfigSection(networkConfigSection);
   }
   
   @Test
   public void testGetVAppTemplateNetworkConnectionSection() {
      NetworkConnectionSection networkConnectionSection = vappTemplateClient.getVAppTemplateNetworkConnectionSection(vAppTemplateURI);

      Checks.checkNetworkConnectionSection(networkConnectionSection);
   }

   @Test
   public void testGetVAppTemplateNetworkSection() {
      NetworkSection networkSection = vappTemplateClient.getVAppTemplateNetworkSection(vAppTemplateURI);

      Checks.checkOvfNetworkSection(networkSection);
   }

   @Test
   public void testGetVAppTemplateOvf() {
      Envelope envelope = vappTemplateClient.getVAppTemplateOvf(vAppTemplateURI);
      
      Checks.checkOvfEnvelope(envelope);
   }

   @Test
   public void testEditVAppTemplate() {
      // TODO Should we be using the orig vappTemplate? Or a blank one with only the name/description?
      
      VAppTemplate oldVAppTemplate = vappTemplateClient.getVAppTemplate(vAppTemplateURI);
      
      String uid = ""+random.nextInt();
      String name = "myname-"+uid;
      String description = "mydescr-"+uid;
      VAppTemplate template = oldVAppTemplate.toBuilder()
               .name(name)
               .description(description)
               .build();
      
      final Task task = vappTemplateClient.editVAppTemplate(vAppTemplateURI, template);
      retryTaskSuccess.apply(task);

      VAppTemplate newTemplate = vappTemplateClient.getVAppTemplate(vAppTemplateURI);
      assertEquals(newTemplate.getName(), name);
      assertEquals(newTemplate.getDescription(), description);
   }

   @Test
   public void testEditVAppTemplateMetadata() {
      // FIXME Cleanup after ourselves..
      
      Metadata oldMetadata = vappTemplateClient.getVAppTemplateMetadata(vAppTemplateURI);
      
      String uid = ""+random.nextInt();
      String key = "mykey-"+uid;
      String val = "myval-"+uid;
      MetadataEntry metadataEntry = MetadataEntry.builder().entry(key, val).build();
      Metadata metadata = Metadata.builder().fromMetadata(oldMetadata).entry(metadataEntry).build();
      
      final Task task = vappTemplateClient.editVAppTemplateMetadata(vAppTemplateURI, metadata);
      retryTaskSuccess.apply(task);

      Metadata newMetadata = vappTemplateClient.getVAppTemplateMetadata(vAppTemplateURI);
      Map<String,String> newMetadataMap = metadataToMap(newMetadata);
      assertEquals(newMetadataMap.get(key), val, "newMetadata="+newMetadata);
   }
   
   private Map<String,String> metadataToMap(Metadata metadata) {
      Map<String,String> result = Maps.newLinkedHashMap();
      for (MetadataEntry entry : metadata.getMetadataEntries()) {
         result.put(entry.getKey(), entry.getValue());
      }
      return result;
   }
   
   @Test
   public void testEditVAppTemplateMetadataValue() {
      // FIXME Cleanup after ourselves..
      
      String uid = ""+random.nextInt();
      String key = "mykey-"+uid;
      String val = "myval-"+uid;
      MetadataValue metadataValue = MetadataValue.builder().value(val).build();
      
      final Task task = vappTemplateClient.editVAppTemplateMetadataValue(vAppTemplateURI, key, metadataValue);
      retryTaskSuccess.apply(task);

      MetadataValue newMetadataValue = vappTemplateClient.getVAppTemplateMetadataValue(vAppTemplateURI, key);
      assertEquals(newMetadataValue.getValue(), metadataValue.getValue());
   }

   @Test
   public void testDeleteVAppTemplateMetadataValue() {
      // First store a value
      String key = "mykey-"+random.nextInt();
      MetadataValue metadataValue = MetadataValue.builder().value("myval").build();
      final Task task = vappTemplateClient.editVAppTemplateMetadataValue(vAppTemplateURI, key, metadataValue);
      retryTaskSuccess.apply(task);
      
      final Task deletionTask = vappTemplateClient.deleteVAppTemplateMetadataValue(vAppTemplateURI, key);
      retryTaskSuccess.apply(deletionTask);

      Metadata newMetadata = vappTemplateClient.getVAppTemplateMetadata(vAppTemplateURI);
      Map<String,String> newMetadataMap = metadataToMap(newMetadata);
      assertFalse(newMetadataMap.containsKey(key), "newMetadata="+newMetadata);
   }

   @Test // FIXME Failing because template does not have a guest customization section to be got
   public void testEditVAppTemplateGuestCustomizationSection() {
      String domainUserName = ""+random.nextInt(Integer.MAX_VALUE);
      GuestCustomizationSection guestCustomizationSection = GuestCustomizationSection.builder()
               .info("my info")
               .domainUserName(domainUserName)
               .enabled(true)
               .build();
      
      final Task task = vappTemplateClient.editVAppTemplateGuestCustomizationSection(vAppTemplateURI, guestCustomizationSection);
      retryTaskSuccess.apply(task);

      GuestCustomizationSection newGuestCustomizationSection = vappTemplateClient.getVAppTemplateGuestCustomizationSection(vAppTemplateURI);
      assertEquals(newGuestCustomizationSection.getDomainUserName(), domainUserName);
   }
   
   @Test
   public void testEditVAppTemplateCustomizationSection() {
      boolean oldVal = vappTemplateClient.getVAppTemplateCustomizationSection(vAppTemplateURI).isCustomizeOnInstantiate();
      boolean newVal = !oldVal;
      
      CustomizationSection customizationSection = CustomizationSection.builder()
               .info("my info")
               .customizeOnInstantiate(newVal)
               .build();
      
      final Task task = vappTemplateClient.editVAppTemplateCustomizationSection(vAppTemplateURI, customizationSection);
      retryTaskSuccess.apply(task);

      CustomizationSection newCustomizationSection = vappTemplateClient.getVAppTemplateCustomizationSection(vAppTemplateURI);
      assertEquals(newCustomizationSection.isCustomizeOnInstantiate(), newVal);
   }

   @Test // FIXME deploymentLeaseInSeconds returned is null 
   public void testEditVAppTemplateLeaseSettingsSection() throws Exception {
      // Note: use smallish number for storageLeaseInSeconds; it seems to be capped at 5184000?
      int storageLeaseInSeconds = random.nextInt(10000)+1;
      int deploymentLeaseInSeconds = random.nextInt(10000)+1;
      LeaseSettingsSection leaseSettingSection = LeaseSettingsSection.builder()
               .info("my info")
               .storageLeaseInSeconds(storageLeaseInSeconds)
               .deploymentLeaseInSeconds(deploymentLeaseInSeconds)
               .build();
      
      final Task task = vappTemplateClient.editVappTemplateLeaseSettingsSection(vAppTemplateURI, leaseSettingSection);
      retryTaskSuccess.apply(task);
      
      LeaseSettingsSection newLeaseSettingsSection = vappTemplateClient.getVappTemplateLeaseSettingsSection(vAppTemplateURI);
      assertEquals(newLeaseSettingsSection.getStorageLeaseInSeconds(), (Integer)storageLeaseInSeconds);
      assertEquals(newLeaseSettingsSection.getDeploymentLeaseInSeconds(), (Integer)deploymentLeaseInSeconds);
   }

   @Test // FIXME Fails with PUT even though that agrees with docs
   public void testEditVAppTemplateNetworkConfigSection() {
      String networkName = ""+random.nextInt();
      NetworkConfiguration networkConfiguration = NetworkConfiguration.builder()
               .fenceMode("isolated")
               .build();
      VAppNetworkConfiguration vappNetworkConfiguration = VAppNetworkConfiguration.builder()
               .networkName(networkName)
               .configuration(networkConfiguration)
               .build();
      Set<VAppNetworkConfiguration> vappNetworkConfigurations = ImmutableSet.of(vappNetworkConfiguration);
      NetworkConfigSection networkConfigSection = NetworkConfigSection.builder()
               .networkConfigs(vappNetworkConfigurations)
               .build();
      
      final Task task = vappTemplateClient.editVAppTemplateNetworkConfigSection(vAppTemplateURI, networkConfigSection);
      retryTaskSuccess.apply(task);

      NetworkConfigSection newNetworkConfigSection = vappTemplateClient.getVAppTemplateNetworkConfigSection(vAppTemplateURI);
      assertEquals(newNetworkConfigSection.getNetworkConfigs().size(), 1);
      
      VAppNetworkConfiguration newVAppNetworkConfig = Iterables.get(newNetworkConfigSection.getNetworkConfigs(), 0);
      assertEquals(newVAppNetworkConfig.getNetworkName(), networkName);
   }

   @Test
   public void testEditVAppTemplateNetworkConnectionSection() {
      String info = ""+random.nextInt();
      NetworkConnectionSection networkConnectionSection = NetworkConnectionSection.builder()
               .info(info)
               .build();
      
      final Task task = vappTemplateClient.editVAppTemplateNetworkConnectionSection(vAppTemplateURI, networkConnectionSection);
      retryTaskSuccess.apply(task);

      NetworkConnectionSection newNetworkConnectionSection = vappTemplateClient.getVAppTemplateNetworkConnectionSection(vAppTemplateURI);
      assertEquals(newNetworkConnectionSection.getInfo(), info);
   }
   
   @Test // FIXME Need to clone a new template so have something to delete
   public void testDeleteVAppTemplate() throws Exception {
      // FIXME fix case of VappTemplate -> VAppTemplate
      String newVAppTemplateId = "something";
      URI newVAppTemplateURI = URI.create(endpoint + "/vAppTemplate/" + newVAppTemplateId);

      final Task task = vappTemplateClient.deleteVappTemplate(newVAppTemplateURI);
      retryTaskSuccess.apply(task);

      // TODO What is the nice way to find out if a template now exists? It's certainly not the code below!
      // But this gives an idea of what I'm trying to do.
      try {
         vappTemplateClient.getVAppTemplate(newVAppTemplateURI);
      } catch (NoSuchElementException e) {
         // success
      }
   }

   @Test
   public void testDisableVAppTemplateDownload() throws Exception {
      // TODO Need assertion that command had effect
      final Task task = vappTemplateClient.disableDownloadVappTemplate(vAppTemplateURI);
      retryTaskSuccess.apply(task);
   }
   
   @Test
   public void testEnableVAppTemplateDownload() throws Exception {
      // TODO Need assertion that command had effect
      final Task task = vappTemplateClient.enableDownloadVappTemplate(vAppTemplateURI);
      retryTaskSuccess.apply(task);
   }
   
   @Test
   public void testConsolidateVAppTemplate() throws Exception {
      // TODO Need assertion that command had effect
      final Task task = vappTemplateClient.consolidateVappTemplate(vAppTemplateURI);
      retryTaskSuccess.apply(task);
   }
   
   @Test
   public void testRelocateVAppTemplate() throws Exception {
      // TODO Need assertion that command had effect
      Reference dataStore = null; // FIXME
      RelocateParams relocateParams = RelocateParams.builder()
               .datastore(dataStore)
               .build();
      
      final Task task = vappTemplateClient.relocateVappTemplate(vAppTemplateURI, relocateParams);
      retryTaskSuccess.apply(task);
   }
}
