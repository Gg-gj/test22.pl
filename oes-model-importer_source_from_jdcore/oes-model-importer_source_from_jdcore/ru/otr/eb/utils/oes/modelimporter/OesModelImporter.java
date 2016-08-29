package ru.otr.eb.utils.oes.modelimporter;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import oracle.security.jps.search.SearchQuery.MATCHER;
import oracle.security.jps.service.policystore.ApplicationPolicy;
import oracle.security.jps.service.policystore.PolicyStoreException;
import oracle.security.jps.service.policystore.entitymanager.AppRoleManager;
import oracle.security.jps.service.policystore.entitymanager.ExtensionManager;
import oracle.security.jps.service.policystore.info.AppRoleEntry;
import oracle.security.jps.service.policystore.info.AttributeEntry;
import oracle.security.jps.service.policystore.info.DataType;
import oracle.security.jps.service.policystore.info.Expression;
import oracle.security.jps.service.policystore.info.PolicyEntry;
import oracle.security.jps.service.policystore.info.PrincipalEntry;
import oracle.security.jps.service.policystore.info.resource.ResourceActionsEntry;
import oracle.security.jps.service.policystore.info.resource.ResourceEntry;
import oracle.security.jps.service.policystore.info.resource.ResourceTypeEntry;
import oracle.security.jps.service.policystore.search.ComparatorType;
import oracle.security.jps.service.policystore.search.ResourceSearchQuery;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import ru.otr.eb.utils.oes.modelimporter.model.TApplicationDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TApplicationRoleDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TAttributeTypeEnum;
import ru.otr.eb.utils.oes.modelimporter.model.TAutorizationPolicyDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TGroupPrincipalDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TObligationAttribute;
import ru.otr.eb.utils.oes.modelimporter.model.TParameter;
import ru.otr.eb.utils.oes.modelimporter.model.TResourceActionsRef;
import ru.otr.eb.utils.oes.modelimporter.model.TResourceDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TResourceTypeDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TUserPrincipalDesc;

public class OesModelImporter
{
  private static Logger log = org.slf4j.LoggerFactory.getLogger(OesModelImporter.class);
  private static java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("d MMM yyyy HH:mm:ss");
  private ModelLoader modelLoader;
  private ru.otr.eb.utils.oes.OesPolicyStoreConnector oesPolicyStoreConnector;
  private LdapDao ldapDao;
  private ru.otr.eb.utils.oes.common.OESPdpPolicyDistributor oesPdpPolicyDistributor;
  private ru.otr.eb.utils.oes.modelimporter.actions.ActionsNamesService actionsNamesService;
  private ImportDataInfoProvider importDataInfoProvider;
  private boolean policyStoreClearBeforeImport;
  private String[] importingApplications;
  private boolean registerOnlyResources;
  private boolean policyAddNotExistentPrincipals;
  private boolean distributeOnly;
  
  public OesModelImporter() {}
  
  public void doImport()
  {
    ru.otr.eb.utils.oes.modelimporter.model.TPolicyStoreDesc policyStoreDesc = modelLoader.getPolicyStoreDesc();
    if (policyStoreDesc == null) {
      log.error("Policy store desc is not initialized"); return;
    }
    
    oracle.security.jps.service.policystore.BindingPolicyStore bindingPolicyStore;
    
    try
    {
      bindingPolicyStore = oesPolicyStoreConnector.connectPolicyStore();
    } catch (oracle.security.jps.JpsException e) {
      log.error("Policy store connection error", e);
      return;
    }
    

    Importer importer = new Importer(policyStoreDesc, bindingPolicyStore, null);
    try {
      importer.doImport();
    } catch (Exception e) {
      log.error("Model import error", e);
    }
  }
  
  public void setPolicyStoreClearBeforeImport(boolean policyStoreClearBeforeImport)
  {
    this.policyStoreClearBeforeImport = policyStoreClearBeforeImport;
  }
  
  public void setLdapDao(LdapDao ldapDao) {
    this.ldapDao = ldapDao;
  }
  
  public void setModelLoader(ModelLoader modelLoader) {
    this.modelLoader = modelLoader;
  }
  
  public void setOesPolicyStoreConnector(ru.otr.eb.utils.oes.OesPolicyStoreConnector oesPolicyStoreConnector) {
    this.oesPolicyStoreConnector = oesPolicyStoreConnector;
  }
  
  public void setOesPdpPolicyDistributor(ru.otr.eb.utils.oes.common.OESPdpPolicyDistributor oesPdpPolicyDistributor) {
    this.oesPdpPolicyDistributor = oesPdpPolicyDistributor;
  }
  
  public void setImportingApplications(String[] importingApplications) {
    this.importingApplications = importingApplications;
  }
  
  public void setRegisterOnlyResources(boolean registerOnlyResources) {
    this.registerOnlyResources = registerOnlyResources;
  }
  
  public void setPolicyAddNotExistentPrincipals(boolean policyAddNotExistentPrincipals) {
    this.policyAddNotExistentPrincipals = policyAddNotExistentPrincipals;
  }
  
  public void setActionsNamesService(ru.otr.eb.utils.oes.modelimporter.actions.ActionsNamesService actionsNamesService) {
    this.actionsNamesService = actionsNamesService;
  }
  
  public void setDistributeOnly(boolean distributeOnly) {
    this.distributeOnly = distributeOnly;
  }
  
  public void setImportDataInfoProvider(ImportDataInfoProvider importDataInfoProvider) {
    this.importDataInfoProvider = importDataInfoProvider;
  }
  
  private class Importer {
    private ru.otr.eb.utils.oes.modelimporter.model.TPolicyStoreDesc policyStoreDesc;
    private oracle.security.jps.service.policystore.BindingPolicyStore bindingPolicyStore;
    private ApplicationPolicy currentApplicationPolicy;
    
    private Importer(ru.otr.eb.utils.oes.modelimporter.model.TPolicyStoreDesc policyStoreDesc, oracle.security.jps.service.policystore.BindingPolicyStore bindingPolicyStore) {
      this.policyStoreDesc = policyStoreDesc;
      this.bindingPolicyStore = bindingPolicyStore;
    }
    
    public void doImport() throws PolicyStoreException, ParseException {
      OesModelImporter.log.info("START IMPORT");
      for (TApplicationDesc applicationDesc : policyStoreDesc.getApplication()) {
        try {
          doImportApplication(applicationDesc);
          addInfoToApplicationDescription("Successfull finish time: " + OesModelImporter.access$200());
        } catch (PolicyStoreException e) {
          addInfoToApplicationDescription("Error finish time: " + OesModelImporter.access$200() + ", error: " + e.getMessage());
          throw e;
        } catch (ParseException e) {
          addInfoToApplicationDescription("Error finish time: " + OesModelImporter.access$200() + ", error: " + e.getMessage());
          throw e;
        } catch (RuntimeException e) {
          addInfoToApplicationDescription("Error finish time: " + OesModelImporter.access$200() + ", error: " + e.getMessage());
          throw e;
        }
      }
      OesModelImporter.log.info("FINISH IMPORT");
    }
    
    private void addInfoToApplicationDescription(String info) throws PolicyStoreException {
      if (currentApplicationPolicy == null) {
        return;
      }
      
      String applicationPolicyDescription = currentApplicationPolicy.getApplicationDescription();
      if (applicationPolicyDescription.length() + info.length() + 1 < 3000) {
        applicationPolicyDescription = applicationPolicyDescription + "\n" + info;
      } else {
        applicationPolicyDescription = org.apache.commons.lang.StringUtils.substring(applicationPolicyDescription, 3000 - info.length() - 1) + "\n" + info;
      }
      
      currentApplicationPolicy.setDescription(applicationPolicyDescription);
      OesModelImporter.log.debug("Starting updating application description");
      bindingPolicyStore.modifyApplicationPolicy(currentApplicationPolicy);
      OesModelImporter.log.debug("Finish updating application description");
    }
    
    public void doImportApplication(TApplicationDesc applicationDesc) throws PolicyStoreException, ParseException {
      OesModelImporter.log.info("================================================================================");
      
      if ((org.apache.commons.lang.ArrayUtils.isNotEmpty(importingApplications)) && (!org.apache.commons.lang.ArrayUtils.contains(importingApplications, applicationDesc.getName()))) {
        OesModelImporter.log.info("Application '{}' is not in importing list {}, skipping", applicationDesc.getName(), org.apache.commons.lang.ArrayUtils.toString(importingApplications));
        return;
      }
      OesModelImporter.log.info("Processing application '{}'", applicationDesc.getName());
      
      String importDataInfo = importDataInfoProvider.getImportDataInfo();
      try
      {
        currentApplicationPolicy = bindingPolicyStore.getApplicationPolicy(applicationDesc.getName());
        
        OesModelImporter.log.debug("Application '{}' is found", applicationDesc.getName());
        addInfoToApplicationDescription("Start time: " + OesModelImporter.access$200() + "\n" + importDataInfo);
        
        if (distributeOnly) {
          OesModelImporter.log.debug("distributeOnly property is true, starting distributing");
          oesPdpPolicyDistributor.doDistribute(currentApplicationPolicy);
          return;
        }
        if (policyStoreClearBeforeImport) {
          OesModelImporter.log.info("Start clearing application '{}'", applicationDesc.getName());
          clearApplication();
          OesModelImporter.log.info("Finish clearing application '{}'", applicationDesc.getName());
        }
      } catch (oracle.security.jps.service.policystore.PolicyObjectNotFoundException e) {
        OesModelImporter.log.info("Application '{}' is not found. Creating...", applicationDesc.getName());
        currentApplicationPolicy = bindingPolicyStore.createApplicationPolicy(applicationDesc.getName(), applicationDesc.getDisplayedName(), importDataInfo);
      }
      
      OesModelImporter.log.debug("Starting import model for application '{}'", applicationDesc.getName());
      

      if ((applicationDesc.getAttributeList() != null) && (CollectionUtils.isNotEmpty(applicationDesc.getAttributeList().getAttribute()))) {
        OesModelImporter.log.debug("Starting import attributes");
        for (ru.otr.eb.utils.oes.modelimporter.model.TAttributeDesc attributeDesc : applicationDesc.getAttributeList().getAttribute()) {
          doImportAttribute(attributeDesc);
        }
      } else {
        OesModelImporter.log.debug("No attributes are defined");
      }
      

      if (registerOnlyResources) {
        OesModelImporter.log.debug("Ignoring roles, because of setting -or=true");
      } else if ((applicationDesc.getRoleList() != null) && (CollectionUtils.isNotEmpty(applicationDesc.getRoleList().getRole()))) {
        OesModelImporter.log.debug("Starting import application roles");
        for (TApplicationRoleDesc applicationRoleDesc : applicationDesc.getRoleList().getRole()) {
          doImportApplicationRole(applicationRoleDesc);
        }
      } else {
        OesModelImporter.log.debug("No application roles are defined");
      }
      

      if (registerOnlyResources) {
        OesModelImporter.log.debug("Ignoring inherited roles, because of setting -or=true");
      } else if ((applicationDesc.getRoleList() != null) && (CollectionUtils.isNotEmpty(applicationDesc.getRoleList().getRole()))) {
        OesModelImporter.log.debug("Starting granting inherited application roles");
        for (TApplicationRoleDesc applicationRoleDesc : applicationDesc.getRoleList().getRole()) {
          doGrantInheretedApplicationRole(applicationRoleDesc);
        }
      }
      

      if ((applicationDesc.getResourceTypeList() != null) && (CollectionUtils.isNotEmpty(applicationDesc.getResourceTypeList().getResourceType()))) {
        OesModelImporter.log.debug("Starting import application resource types");
        for (TResourceTypeDesc resourceTypeDesc : applicationDesc.getResourceTypeList().getResourceType()) {
          doImportResourceType(resourceTypeDesc);
        }
      } else {
        OesModelImporter.log.debug("No resource types are defined");
      }
      

      if (registerOnlyResources) {
        OesModelImporter.log.debug("Ignoring policies, because of setting -or=true");
      } else if ((applicationDesc.getPolicyList() != null) && (CollectionUtils.isNotEmpty(applicationDesc.getPolicyList().getPolicy()))) {
        OesModelImporter.log.debug("Starting import application policies");
        for (TAutorizationPolicyDesc autorizationPolicyDesc : applicationDesc.getPolicyList().getPolicy()) {
          doImportAutorizationPolicy(autorizationPolicyDesc);
        }
      } else {
        OesModelImporter.log.debug("No policies are defined");
      }
      


      oesPdpPolicyDistributor.doDistribute(currentApplicationPolicy);
    }
    
    private class PolicyAutoGenerator {
      private PolicyAutoGenerator() {}
      
      private void doGeneratePolicies() throws PolicyStoreException { oracle.security.jps.service.policystore.entitymanager.ResourceTypeManager resourceTypeManager = currentApplicationPolicy.getResourceTypeManager();
        oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery resourceTypeSearchQuery = new oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery(oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
        
        List<ResourceTypeEntry> resourceTypeEntryList = resourceTypeManager.getResourceTypes(resourceTypeSearchQuery);
        if (CollectionUtils.isNotEmpty(resourceTypeEntryList)) {
          for (ResourceTypeEntry resourceTypeEntry : resourceTypeEntryList) {
            OesModelImporter.log.info("Deleting resource type '{}'", resourceTypeEntry.getName());
            resourceTypeManager.deleteResourceType(resourceTypeEntry.getName(), true);
          }
        } else {
          OesModelImporter.log.debug("No resource type is found for delete");
        }
        

        oracle.security.jps.service.policystore.entitymanager.PolicyManager policyManager = currentApplicationPolicy.getPolicyManager();
        oracle.security.jps.service.policystore.search.PolicySearchQuery policySearchQuery = new oracle.security.jps.service.policystore.search.PolicySearchQuery(oracle.security.jps.service.policystore.search.PolicySearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
        
        List<PolicyEntry> policyEntryList = policyManager.getPolicies(policySearchQuery);
        if (CollectionUtils.isNotEmpty(policyEntryList)) {
          for (PolicyEntry policyEntry : policyEntryList) {
            OesModelImporter.log.info("Deleting policy '{}'", policyEntry.getName());
          }
          
        } else {
          OesModelImporter.log.debug("No policy is found for delete");
        }
      }
    }
    

    private void clearApplication()
      throws PolicyStoreException
    {
      if (registerOnlyResources) {
        OesModelImporter.log.debug("Policies are not deleting, because of setting -or=true");
      } else {
        oracle.security.jps.service.policystore.entitymanager.PolicyManager policyManager = currentApplicationPolicy.getPolicyManager();
        oracle.security.jps.service.policystore.search.PolicySearchQuery policySearchQuery = new oracle.security.jps.service.policystore.search.PolicySearchQuery(oracle.security.jps.service.policystore.search.PolicySearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
        
        List<PolicyEntry> policyEntryList = policyManager.getPolicies(policySearchQuery);
        if (CollectionUtils.isNotEmpty(policyEntryList)) {
          for (PolicyEntry policyEntry : policyEntryList) {
            OesModelImporter.log.info("Deleting policy '{}'", policyEntry.getName());
            policyManager.deletePolicy(policyEntry.getName());
          }
        } else {
          OesModelImporter.log.debug("No policy is found for delete");
        }
      }
      

      oracle.security.jps.service.policystore.entitymanager.ResourceTypeManager resourceTypeManager = currentApplicationPolicy.getResourceTypeManager();
      oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery resourceTypeSearchQuery = new oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery(oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
      
      List<ResourceTypeEntry> resourceTypeEntryList = resourceTypeManager.getResourceTypes(resourceTypeSearchQuery);
      if (CollectionUtils.isNotEmpty(resourceTypeEntryList)) {
        for (ResourceTypeEntry resourceTypeEntry : resourceTypeEntryList) {
          OesModelImporter.log.info("Deleting resource type '{}'", resourceTypeEntry.getName());
          resourceTypeManager.deleteResourceType(resourceTypeEntry.getName(), true);
        }
      } else {
        OesModelImporter.log.debug("No resource type is found for delete");
      }
      

      ExtensionManager extensionManager = currentApplicationPolicy.getExtensionManager();
      oracle.security.jps.service.policystore.search.AttributeSearchQuery attributeSearchQuery = new oracle.security.jps.service.policystore.search.AttributeSearchQuery(oracle.security.jps.service.policystore.search.AttributeSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
      
      List<AttributeEntry<? extends DataType>> attributes = extensionManager.getAttributes(attributeSearchQuery);
      if (CollectionUtils.isNotEmpty(attributes)) {
        for (AttributeEntry<? extends DataType> attributeEntry : attributes) {
          if (!attributeEntry.isBuiltInAttribute()) {
            OesModelImporter.log.info("Deleting attribute '{}'", attributeEntry.getName());
            
            extensionManager.deleteAttribute(attributeEntry.getName(), false);
          }
        }
      } else {
        OesModelImporter.log.debug("No attribute is found for delete");
      }
      

      if (registerOnlyResources) {
        OesModelImporter.log.debug("Policies are not deleting, because of setting -or=true");
      } else {
        AppRoleManager appRoleManager = currentApplicationPolicy.getAppRoleManager();
        oracle.security.jps.service.policystore.search.AppRoleSearchQuery appRoleSearchQuery = new oracle.security.jps.service.policystore.search.AppRoleSearchQuery(oracle.security.jps.service.policystore.search.AppRoleSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
        
        List<AppRoleEntry> appRoleEntryList = appRoleManager.getAppRoles(appRoleSearchQuery);
        if (CollectionUtils.isNotEmpty(appRoleEntryList)) {
          for (AppRoleEntry appRoleEntry : appRoleEntryList) {
            OesModelImporter.log.info("Deleting application role '{}'", appRoleEntry.getName());
            appRoleManager.deleteAppRole(appRoleEntry.getName(), true);
          }
        } else {
          OesModelImporter.log.debug("No application role is found for delete");
        }
      }
      
      actionsNamesService.deleteAll(currentApplicationPolicy.getApplicationName());
    }
    
    private void doImportAttribute(ru.otr.eb.utils.oes.modelimporter.model.TAttributeDesc attributeDesc) throws PolicyStoreException {
      ExtensionManager extensionManager = currentApplicationPolicy.getExtensionManager();
      try {
        extensionManager.getAttribute(attributeDesc.getName());
        OesModelImporter.log.debug("Attribute '{}' is found", attributeDesc.getName());
      } catch (oracle.security.jps.service.policystore.PolicyObjectNotFoundException e) {
        OesModelImporter.log.info("Attribute '{}' is not found. Creating...", attributeDesc.getName());
        extensionManager.createAttribute(attributeDesc.getName(), attributeDesc.getDisplayedName(), attributeDesc.getDisplayedName(), getDataType(attributeDesc.getType()), oracle.security.jps.service.policystore.info.AttributeEntry.AttributeCategory.RESOURCE, true);
      }
    }
    





    private Class<? extends DataType> getDataType(TAttributeTypeEnum attributeType)
    {
      if (TAttributeTypeEnum.BOOLEAN.equals(attributeType))
        return oracle.security.jps.service.policystore.info.OpssBoolean.class;
      if (TAttributeTypeEnum.INTEGER.equals(attributeType))
        return oracle.security.jps.service.policystore.info.OpssInteger.class;
      if (TAttributeTypeEnum.STRING.equals(attributeType))
        return oracle.security.jps.service.policystore.info.OpssString.class;
      if (TAttributeTypeEnum.DATE.equals(attributeType))
        return oracle.security.jps.service.policystore.info.OpssDate.class;
      if (TAttributeTypeEnum.DOUBLE.equals(attributeType)) {
        return oracle.security.jps.service.policystore.info.OpssDouble.class;
      }
      





























      throw new RuntimeException("Invalid attribute type '" + attributeType + "'");
    }
    
    private void doGrantInheretedApplicationRole(TApplicationRoleDesc applicationRoleDesc) throws PolicyStoreException {
      List<AppRoleEntry> appRoleEntryList = getAppRoleEntryList(applicationRoleDesc.getName());
      

      if (appRoleEntryList.size() > 1) {
        OesModelImporter.log.error("{} application role '{}' found. Exit", Integer.valueOf(appRoleEntryList.size()), applicationRoleDesc.getName());
        return;
      }
      
      AppRoleEntry appRoleEntry = (AppRoleEntry)appRoleEntryList.get(0);
      
      if ((applicationRoleDesc.getRoles() != null) && (CollectionUtils.isNotEmpty(applicationRoleDesc.getRoles().getRole()))) {
        AppRoleManager appRoleManager = currentApplicationPolicy.getAppRoleManager();
        List<PrincipalEntry> rolesForGrantList = new java.util.ArrayList();
        for (ru.otr.eb.utils.oes.modelimporter.model.TRolePrincipalDesc inheritedRoleFromModel : applicationRoleDesc.getRoles().getRole()) {
          List<AppRoleEntry> inheridedRoleFromDBList = getAppRoleEntryList(inheritedRoleFromModel.getName());
          
          if (inheridedRoleFromDBList.size() > 1) {
            OesModelImporter.log.error("{} inherited application role '{}' found. Exit", Integer.valueOf(inheridedRoleFromDBList.size()), inheritedRoleFromModel.getName());
          }
          else if (inheridedRoleFromDBList.size() == 0) {
            OesModelImporter.log.error("Inherited application role '{}' not found for role {}. Exit", inheritedRoleFromModel.getName(), applicationRoleDesc.getName());
          }
          else
            rolesForGrantList.add(inheridedRoleFromDBList.get(0));
        }
        OesModelImporter.log.info("Adding inherited roles '{}' for application role '{}'", rolesForGrantList, applicationRoleDesc.getName());
        appRoleManager.grantAppRole(appRoleEntry, rolesForGrantList);
        appRoleManager.modifyAppRole(appRoleEntry);
      } else {
        OesModelImporter.log.debug("Application role '{}' has no inherited roles", applicationRoleDesc.getName());
      }
    }
    
    private List<AppRoleEntry> getAppRoleEntryList(String appRoleName) throws PolicyStoreException {
      AppRoleManager appRoleManager = currentApplicationPolicy.getAppRoleManager();
      
      oracle.security.jps.service.policystore.search.AppRoleSearchQuery query = new oracle.security.jps.service.policystore.search.AppRoleSearchQuery(oracle.security.jps.service.policystore.search.AppRoleSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, appRoleName, SearchQuery.MATCHER.EXACT);
      
      return appRoleManager.getAppRoles(query);
    }
    
    private void doImportApplicationRole(TApplicationRoleDesc applicationRoleDesc) throws PolicyStoreException {
      AppRoleManager appRoleManager = currentApplicationPolicy.getAppRoleManager();
      
      oracle.security.jps.service.policystore.search.AppRoleSearchQuery query = new oracle.security.jps.service.policystore.search.AppRoleSearchQuery(oracle.security.jps.service.policystore.search.AppRoleSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, applicationRoleDesc.getName(), SearchQuery.MATCHER.EXACT);
      
      List<AppRoleEntry> appRoleEntryList = appRoleManager.getAppRoles(query);
      

      if (appRoleEntryList.size() > 1) {
        OesModelImporter.log.error("{} application role '{}' found. Exit", Integer.valueOf(appRoleEntryList.size()), applicationRoleDesc.getName());
        return;
      }
      

      if (appRoleEntryList.size() == 0) {
        OesModelImporter.log.info("Application role '{}' is not found. Creating..", applicationRoleDesc.getName());
        AppRoleEntry appRoleEntry = appRoleManager.createAppRole(applicationRoleDesc.getName(), applicationRoleDesc.getDisplayedName(), applicationRoleDesc.getDisplayedName());
        if (!isStoredApplicationRoleEqualToImporting(appRoleEntry, applicationRoleDesc)) {
          OesModelImporter.log.info("Created application role '{}' is modified by principals. Updating in store...", applicationRoleDesc.getName());
          appRoleManager.modifyAppRole(appRoleEntry);
        }
      }
      else {
        OesModelImporter.log.debug("Application role '{}' is found", applicationRoleDesc.getName());
        AppRoleEntry appRoleEntry = (AppRoleEntry)appRoleEntryList.get(0);
        
        if (!isStoredApplicationRoleEqualToImporting(appRoleEntry, applicationRoleDesc)) {
          OesModelImporter.log.info("Application role '{}' is changing. Updating in store...", applicationRoleDesc.getName());
          appRoleManager.modifyAppRole(appRoleEntry);
        }
      }
    }
    
    private boolean isStoredApplicationRoleEqualToImporting(AppRoleEntry appRoleEntry, TApplicationRoleDesc applicationRoleDesc)
      throws PolicyStoreException
    {
      boolean isModified = false;
      if (!appRoleEntry.getDisplayName().equals(applicationRoleDesc.getDisplayedName())) {
        OesModelImporter.log.info("Display name for application role '{}' changing from '{}' to '{}'", new Object[] { applicationRoleDesc.getName(), appRoleEntry.getDisplayName(), applicationRoleDesc.getDisplayedName() });
        appRoleEntry.setDisplayName(applicationRoleDesc.getDisplayedName());
        isModified = true;
      }
      

      List<java.security.Principal> currentApplicationRolePrincipals = currentApplicationPolicy.getAppRolesMembers(applicationRoleDesc.getName());
      








      if (applicationRoleDesc.getUsers() != null) {
        for (TUserPrincipalDesc userPrincipalDesc : applicationRoleDesc.getUsers().getUser()) {
          String userCN = getUserCNFromLdap(userPrincipalDesc);
          
          if (org.apache.commons.lang.StringUtils.isEmpty(userCN)) {
            if (policyAddNotExistentPrincipals) {
              userCN = userPrincipalDesc.getSn();
              if (org.apache.commons.lang.StringUtils.isEmpty(userCN)) {
                userCN = userPrincipalDesc.getCn();
              }
              OesModelImporter.log.debug("User {cn: '{}', sn: '{}'} is not found in ldap, but not skip this user", userPrincipalDesc.getCn(), userPrincipalDesc.getSn());
            } else {
              OesModelImporter.log.error("User {cn: '{}', sn: '{}'} is not found in ldap, skip this user", userPrincipalDesc.getCn(), userPrincipalDesc.getSn());
            }
          }
          else {
            OesModelImporter.log.debug("User {cn: '{}', sn: '{}'} is found in ldap", userPrincipalDesc.getCn(), userPrincipalDesc.getSn());
          }
          
          final String finalUserCN = userCN;
          if (CollectionUtils.select(currentApplicationRolePrincipals, new org.apache.commons.collections.Predicate()
          {
            public boolean evaluate(Object object) {
              java.security.Principal principal = (java.security.Principal)object;
              return (principal.getClass().equals(weblogic.security.principal.WLSUserImpl.class)) && (principal.getName().equals(finalUserCN));
            }
          }).size() == 0)
          {






            OesModelImporter.log.info("User {cn: '{}', sn: '{}'} is not linked to application role '{}'. Linking...", new Object[] { userCN, userPrincipalDesc.getSn(), applicationRoleDesc.getName() });
            
            PrincipalEntry pe = createUserPrincipalEntry(userCN);
            currentApplicationPolicy.addPrincipalToAppRole(pe, applicationRoleDesc.getName());
            isModified = true;
          } else {
            OesModelImporter.log.debug("User {cn:'{}', sn: '{}'} is already linked to application role '{}'", new Object[] { userCN, userPrincipalDesc.getSn(), applicationRoleDesc.getName() });
          }
        }
      }
      



      if (applicationRoleDesc.getGroups() != null) {
        for (TGroupPrincipalDesc groupPrincipalDesc : applicationRoleDesc.getGroups().getGroup()) {
          final String groupCn = groupPrincipalDesc.getCn();
          
          if (!ldapDao.isGroupExistByCn(groupCn)) {
            if (policyAddNotExistentPrincipals) {
              OesModelImporter.log.info("Group '{}' is not found in ldap,  but not skip this group", groupPrincipalDesc.getCn(), applicationRoleDesc.getName());
            } else {
              OesModelImporter.log.error("Group '{}' is not found in ldap,  skip this group", groupPrincipalDesc.getCn(), applicationRoleDesc.getName());
            }
          }
          else {
            OesModelImporter.log.debug("Group '{}' is found in ldap", groupCn);
          }
          if (CollectionUtils.select(currentApplicationRolePrincipals, new org.apache.commons.collections.Predicate()
          {
            public boolean evaluate(Object object) {
              java.security.Principal principal = (java.security.Principal)object;
              return (principal.getClass().equals(weblogic.security.principal.WLSGroupImpl.class)) && (principal.getName().equals(groupCn));
            }
          }).size() == 0)
          {






            OesModelImporter.log.info("Group {name:'{}'} is not linked to application role '{}'. Linking...", groupCn, applicationRoleDesc.getName());
            PrincipalEntry pe = createGroupPrincipalEntry(groupCn);
            currentApplicationPolicy.addPrincipalToAppRole(pe, applicationRoleDesc.getName());
            isModified = true;
          } else {
            OesModelImporter.log.debug("Group {name: '{}'} is already linked to application role '{}'", groupCn, applicationRoleDesc.getName());
          }
        }
      }
      
      return !isModified;
    }
    
    private void doImportResourceType(TResourceTypeDesc resourceTypeDesc) throws PolicyStoreException {
      oracle.security.jps.service.policystore.entitymanager.ResourceTypeManager resourceTypeManager = currentApplicationPolicy.getResourceTypeManager();
      oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery rtQuery = new oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery(oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, resourceTypeDesc.getName(), SearchQuery.MATCHER.EXACT);
      
      List<ResourceTypeEntry> resourceTypeEntryList = resourceTypeManager.getResourceTypes(rtQuery);
      

      if (resourceTypeEntryList.size() > 1) {
        OesModelImporter.log.error("{} resource type '{}' is found. Exit", Integer.valueOf(resourceTypeEntryList.size()), resourceTypeDesc.getName());
        return;
      }
      
      ResourceTypeEntry resourceTypeEntry = null;
      
      if (resourceTypeEntryList.size() == 0) {
        OesModelImporter.log.info("Resource type '{}' is not found. Creating..", resourceTypeDesc.getName());
        resourceTypeEntry = resourceTypeManager.createResourceType(resourceTypeDesc.getName(), resourceTypeDesc.getDisplayedName(), resourceTypeDesc.getDisplayedName(), getActionNameList(resourceTypeDesc), getAttributeList(resourceTypeDesc), ",");



      }
      else
      {


        OesModelImporter.log.debug("Resource type '{}' is found", resourceTypeDesc.getName());
        resourceTypeEntry = (ResourceTypeEntry)resourceTypeEntryList.get(0);
        
        if (!isStoredResourceTypeEqualToImporting(resourceTypeEntry, resourceTypeDesc)) {
          OesModelImporter.log.info("Resource type '{}' is changing. Updating resource type in store...", resourceTypeDesc.getName());
          resourceTypeManager.modifyResourceType(resourceTypeEntry);
        }
      }
      

      if ((resourceTypeDesc.getResources() != null) && (CollectionUtils.isNotEmpty(resourceTypeDesc.getResources().getResource()))) {
        OesModelImporter.log.debug("Starting import resources for resource type '{}'", resourceTypeDesc.getName());
        for (TResourceDesc resourceDesc : resourceTypeDesc.getResources().getResource()) {
          doImportResource(resourceTypeEntry, resourceDesc);
        }
      }
    }
    
    private boolean isStoredResourceTypeEqualToImporting(ResourceTypeEntry resourceTypeEntry, TResourceTypeDesc resourceTypeDesc)
      throws PolicyStoreException
    {
      boolean isModified = false;
      if (!"ANY".equals(resourceTypeEntry.getAllAction())) {
        OesModelImporter.log.info("All action name is not 'ANY'. Need to change to 'ANY' current value '{}'", resourceTypeEntry.getAllAction());
        resourceTypeEntry.setAllAction("ANY");
        isModified = true;
      }
      
      if (!resourceTypeEntry.getDisplayName().equals(resourceTypeDesc.getDisplayedName())) {
        OesModelImporter.log.info("Display name for resource type '{}' changing from '{}' to '{}'", new Object[] { resourceTypeDesc.getName(), resourceTypeEntry.getDisplayName(), resourceTypeDesc.getDisplayedName() });
        resourceTypeEntry.setDisplayName(resourceTypeDesc.getDisplayedName());
        isModified = true;
      }
      
      java.util.Set<String> storedActionSet = resourceTypeEntry.getActions();
      List<String> importingActionSet = getActionNameList(resourceTypeDesc);
      Collection<String> newActionList = CollectionUtils.subtract(importingActionSet, storedActionSet);
      if (newActionList.size() > 0) {
        OesModelImporter.log.info("Action list for resource type '{}' changed. New actions '{}' added", resourceTypeDesc.getName(), java.util.Arrays.toString(newActionList.toArray()));
        storedActionSet.addAll(newActionList);
        
        isModified = true;
      } else {
        OesModelImporter.log.debug("Action list for resource type '{}' is not changed. Actual list '{}'", resourceTypeDesc.getName(), java.util.Arrays.toString(storedActionSet.toArray()));
      }
      
      final List<AttributeEntry<? extends DataType>> storedAttributeList = resourceTypeEntry.getValidResourceAttributes();
      List<AttributeEntry<? extends DataType>> importingAttributeList = getAttributeList(resourceTypeDesc);
      Collection<AttributeEntry<? extends DataType>> newAttributeList = CollectionUtils.select(importingAttributeList, new org.apache.commons.collections.Predicate()
      {
        public boolean evaluate(final Object importingAttributeEntry) {
          null == CollectionUtils.find(storedAttributeList, new org.apache.commons.collections.Predicate()
          {
            public boolean evaluate(Object storedAttributeEntry) {
              return ((AttributeEntry)storedAttributeEntry).getName().equals(((AttributeEntry)importingAttributeEntry).getName());
            }
          });
        }
      });
      
      if (newAttributeList.size() > 0) {
        Collection newAttributeNames = CollectionUtils.transformedCollection(newAttributeList, new org.apache.commons.collections.Transformer()
        {
          public Object transform(Object input) {
            return ((AttributeEntry)input).getName();
          }
        });
        OesModelImporter.log.info("Attribute list for resource type '{}' changed. New actions '{}' added", resourceTypeDesc.getName(), java.util.Arrays.toString(newAttributeNames.toArray()));
        
        for (AttributeEntry<? extends DataType> attributeEntry : newAttributeList) {
          resourceTypeEntry.addValidResourceAttribute(attributeEntry);
        }
        isModified = true;
      } else {
        OesModelImporter.log.debug("Attribute list for resource type '{}' is not changed", resourceTypeDesc.getName());
      }
      
      return !isModified;
    }
    
    private void doImportResource(ResourceTypeEntry resourceTypeEntry, TResourceDesc resourceDesc) throws PolicyStoreException {
      ResourceSearchQuery resourceTypeQuery = new ResourceSearchQuery(oracle.security.jps.service.policystore.search.ResourceSearchQuery.SEARCH_PROPERTY.RESOURCE_TYPE, false, ComparatorType.EQUALITY, resourceTypeEntry.getName(), SearchQuery.MATCHER.EXACT);
      
      ResourceSearchQuery resourceNameQuery = new ResourceSearchQuery(oracle.security.jps.service.policystore.search.ResourceSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, resourceDesc.getName(), SearchQuery.MATCHER.EXACT);
      
      ResourceSearchQuery query = new ResourceSearchQuery(java.util.Arrays.asList(new ResourceSearchQuery[] { resourceTypeQuery, resourceNameQuery }), false, false);
      List<ResourceEntry> resourceEntryList = currentApplicationPolicy.getResourceManager().getResources(query);
      
      if (resourceEntryList.size() == 0) {
        OesModelImporter.log.info("Resource '{}/{}' is not found. Creating... ", resourceTypeEntry.getName(), resourceDesc.getName());
        currentApplicationPolicy.getResourceManager().createResource(resourceDesc.getName(), resourceDesc.getDisplayedName(), resourceDesc.getDisplayedName(), resourceTypeEntry, null);


      }
      else
      {

        OesModelImporter.log.debug("Resource '{}/{}' is already registered", resourceTypeEntry.getName(), resourceDesc.getName());
        ResourceEntry resourceEntry = (ResourceEntry)resourceEntryList.get(0);
        
        if (!isStoredResourceEqualToImporting(resourceEntry, resourceDesc, resourceTypeEntry)) {
          OesModelImporter.log.info("Resource '{}/{}' is changed. Updating resource in store...", resourceTypeEntry.getName(), resourceDesc.getName());
          currentApplicationPolicy.getResourceManager().modifyResource(resourceEntry);
        }
      }
    }
    
    private boolean isStoredResourceEqualToImporting(ResourceEntry resourceEntry, TResourceDesc resourceDesc, ResourceTypeEntry resourceTypeEntry)
      throws PolicyStoreException
    {
      boolean isModified = false;
      if (!resourceEntry.getDisplayName().equals(resourceDesc.getDisplayedName())) {
        OesModelImporter.log.info("Display name for resource '{}/{}' changing from '{}' to '{}'", new Object[] { resourceTypeEntry.getName(), resourceDesc.getName(), resourceEntry.getDisplayName(), resourceDesc.getDisplayedName() });
        resourceEntry.setDisplayName(resourceDesc.getDisplayedName());
        isModified = true;
      }
      
      return !isModified;
    }
    
    private List<String> getActionNameList(TResourceTypeDesc resourceTypeDesc)
    {
      List<String> actionNameList = new java.util.ArrayList();
      if (resourceTypeDesc.getActions() != null) {
        for (ru.otr.eb.utils.oes.modelimporter.model.TActionDesc actionDesc : resourceTypeDesc.getActions().getAction()) {
          actionsNamesService.createOrUpdate(currentApplicationPolicy.getApplicationName(), resourceTypeDesc.getName(), actionDesc.getName(), actionDesc.getDisplayedName());
          
          actionNameList.add(actionDesc.getName());
        }
      }
      return actionNameList;
    }
    
    private List<AttributeEntry<? extends DataType>> getAttributeList(TResourceTypeDesc resourceTypeDesc) throws PolicyStoreException
    {
      List<AttributeEntry<? extends DataType>> attributeEntryList = new java.util.ArrayList();
      
      if (resourceTypeDesc.getAttributes() != null) {
        for (ru.otr.eb.utils.oes.modelimporter.model.TResourceTypeAttributeDesc resourceTypeAttributeDesc : resourceTypeDesc.getAttributes().getAttribute()) {
          String attributeName = resourceTypeAttributeDesc.getName();
          ExtensionManager extensionManager = currentApplicationPolicy.getExtensionManager();
          attributeEntryList.add(extensionManager.getAttribute(attributeName));
        }
      }
      return attributeEntryList;
    }
    
    private void doImportAutorizationPolicy(TAutorizationPolicyDesc autorizationPolicyDesc) throws PolicyStoreException, ParseException {
      oracle.security.jps.service.policystore.entitymanager.PolicyManager policyManager = currentApplicationPolicy.getPolicyManager();
      try
      {
        PolicyEntry policyEntry = policyManager.getPolicy(autorizationPolicyDesc.getName());
        OesModelImporter.log.debug("Policy '{}' is found", autorizationPolicyDesc.getName());
        

        if (!isStoredPolicyEqualToImporting(policyEntry, autorizationPolicyDesc)) {
          OesModelImporter.log.info("Policy '{}' is changed. Updating in store...", autorizationPolicyDesc.getName());
          policyManager.modifyPolicy(policyEntry);
        }
      } catch (oracle.security.jps.service.policystore.PolicyObjectNotFoundException e) {
        OesModelImporter.log.debug("Policy '{}' is not found. Creating...", autorizationPolicyDesc.getName());
        policyManager.createPolicy(autorizationPolicyDesc.getName(), autorizationPolicyDesc.getDisplayedName(), autorizationPolicyDesc.getDisplayedName(), new oracle.security.jps.service.policystore.info.BasicPolicyRuleEntry(autorizationPolicyDesc.getName() + "Rule", "пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ \"" + autorizationPolicyDesc.getDisplayedName() + "\"", "пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ \"" + autorizationPolicyDesc.getDisplayedName() + "\"", oracle.security.jps.service.policystore.info.PolicyRuleEntry.EffectType.GRANT, getRuleExpressionEntry(autorizationPolicyDesc.getCondition())), getPrincipalEntryList(autorizationPolicyDesc), null, createResourceActionsEntryList(autorizationPolicyDesc.getResources().getResource()), null, createObligations(autorizationPolicyDesc.getObligations()), oracle.security.jps.service.policystore.info.PolicyEntry.POLICY_SEMANTIC.OR);
      }
    }
    




    private List<oracle.security.jps.service.policystore.info.ObligationEntry> createObligations(ru.otr.eb.utils.oes.modelimporter.model.TObligations obligations)
      throws ParseException, PolicyStoreException
    {
      if ((obligations == null) || (obligations.getObligation() == null) || (obligations.getObligation().isEmpty())) {
        return null;
      }
      List<oracle.security.jps.service.policystore.info.ObligationEntry> resultList = new java.util.ArrayList();
      OesModelImporter.log.debug("Policy contains obligations, processing");
      for (ru.otr.eb.utils.oes.modelimporter.model.TObligation obligation : obligations.getObligation()) {
        List<oracle.security.jps.service.policystore.info.AttributeAssignment<? extends DataType>> addingAttributes = new java.util.ArrayList();
        if (obligation.getConditionatribute() != null) {
          for (ru.otr.eb.utils.oes.modelimporter.model.TObligationConditionAttribute conditionAttribute : obligation.getConditionatribute()) {
            String conditionValue = getRuleExpressionEntry(conditionAttribute).toString();
            OesModelImporter.log.debug("Adding obligation condition attribute '{}': '{}'", conditionAttribute.getName(), conditionValue);
            addingAttributes.add(new oracle.security.jps.service.policystore.info.AttributeAssignment(conditionAttribute.getName(), new oracle.security.jps.service.policystore.info.OpssString(conditionValue)));
          }
          for (TObligationAttribute attribute : obligation.getAttribute()) {
            DataType dataType;
            if (TAttributeTypeEnum.BOOLEAN.equals(attribute.getType())) {
              dataType = new oracle.security.jps.service.policystore.info.OpssBoolean(Boolean.valueOf(attribute.getValue()).booleanValue()); } else { DataType dataType;
              if (TAttributeTypeEnum.INTEGER.equals(attribute.getType())) {
                dataType = new oracle.security.jps.service.policystore.info.OpssInteger(Integer.valueOf(attribute.getValue()).intValue()); } else { DataType dataType;
                if (TAttributeTypeEnum.STRING.equals(attribute.getType())) {
                  dataType = new oracle.security.jps.service.policystore.info.OpssString(attribute.getValue()); } else { DataType dataType;
                  if (TAttributeTypeEnum.DATE.equals(attribute.getType())) {
                    dataType = new oracle.security.jps.service.policystore.info.OpssDate(OesModelImporter.dateFormat.parse(attribute.getValue())); } else { DataType dataType;
                    if (TAttributeTypeEnum.DOUBLE.equals(attribute.getType())) {
                      dataType = new oracle.security.jps.service.policystore.info.OpssDouble(Double.parseDouble(attribute.getValue()));
                    } else
                      throw new RuntimeException("Invalid obligation attribute type '" + attribute.getType() + "'");
                  } } } }
            DataType dataType;
            OesModelImporter.log.debug("Adding obligation attribute '{}': '{}'", attribute.getName(), attribute.getValue());
            addingAttributes.add(new oracle.security.jps.service.policystore.info.AttributeAssignment(attribute.getName(), dataType));
          }
        }
        


        oracle.security.jps.service.policystore.info.ObligationEntry obligationEntry = new oracle.security.jps.service.policystore.info.BasicObligationEntry(obligation.getName(), obligation.getDisplayedName(), obligation.getDisplayedName(), addingAttributes);
        
        resultList.add(obligationEntry);
      }
      
      return resultList;
    }
    





    private List<PrincipalEntry> getPrincipalEntryList(TAutorizationPolicyDesc autorizationPolicyDesc)
      throws PolicyStoreException
    {
      List<PrincipalEntry> principalEntryList = new java.util.ArrayList();
      

      AppRoleManager appRoleManager = currentApplicationPolicy.getAppRoleManager();
      if ((autorizationPolicyDesc.getRoles() != null) && (CollectionUtils.isNotEmpty(autorizationPolicyDesc.getRoles().getRolename()))) {
        for (ru.otr.eb.utils.oes.modelimporter.model.TApplicationRoleName appRoleName : autorizationPolicyDesc.getRoles().getRolename()) {
          principalEntryList.add(appRoleManager.getAppRole(appRoleName.getName()));
          OesModelImporter.log.debug("Application role '{}' added to authorization policy '{}'", appRoleName.getName(), autorizationPolicyDesc.getName());
        }
      }
      

      if ((autorizationPolicyDesc.getGroups() != null) && (CollectionUtils.isNotEmpty(autorizationPolicyDesc.getGroups().getGroup()))) {
        for (TGroupPrincipalDesc groupPrincipalDesc : autorizationPolicyDesc.getGroups().getGroup()) {
          if (ldapDao.isGroupExistByCn(groupPrincipalDesc.getCn())) {
            OesModelImporter.log.debug("Group {cn: '{}''} for authorization policy '{}' is found in ldap", groupPrincipalDesc.getCn(), autorizationPolicyDesc.getName());
            principalEntryList.add(createUserPrincipalEntry(groupPrincipalDesc.getCn()));
          }
          else if (policyAddNotExistentPrincipals) {
            OesModelImporter.log.debug("Group {cn: '{}''} for authorization policy '{}' is NOT found in ldap,  but not skip this group", groupPrincipalDesc.getCn(), autorizationPolicyDesc.getName());
            principalEntryList.add(createUserPrincipalEntry(groupPrincipalDesc.getCn()));
          } else {
            OesModelImporter.log.error("Group {cn: '{}''} for authorization policy '{}' is NOT found in ldap, skip this group", groupPrincipalDesc.getCn(), autorizationPolicyDesc.getName());
          }
        }
      }
      


      if ((autorizationPolicyDesc.getUsers() != null) && (CollectionUtils.isNotEmpty(autorizationPolicyDesc.getUsers().getUser()))) {
        for (TUserPrincipalDesc userPrincipalDesc : autorizationPolicyDesc.getUsers().getUser()) {
          String userCN = getUserCNFromLdap(userPrincipalDesc);
          
          if (org.apache.commons.lang.StringUtils.isNotEmpty(userCN)) {
            OesModelImporter.log.debug("User {cn: '{}', sn: '{}'} for authorization policy '{}' is found in ldap", new Object[] { userPrincipalDesc.getCn(), userPrincipalDesc.getSn(), autorizationPolicyDesc.getName() });
            principalEntryList.add(createUserPrincipalEntry(userCN));
          }
          else if (policyAddNotExistentPrincipals) {
            OesModelImporter.log.error("User {cn: '{}', sn: '{}'} for authorization policy '{}' is NOT found in ldap,  but not skip this user", new Object[] { userPrincipalDesc.getCn(), userPrincipalDesc.getSn(), autorizationPolicyDesc.getName() });
            userCN = userPrincipalDesc.getSn();
            if (org.apache.commons.lang.StringUtils.isEmpty(userCN)) {
              userCN = userPrincipalDesc.getCn();
            }
            principalEntryList.add(createUserPrincipalEntry(userCN));
          } else {
            OesModelImporter.log.error("User {cn: '{}', sn: '{}'} for authorization policy '{}' is NOT found in ldap,  skip this user", new Object[] { userPrincipalDesc.getCn(), userPrincipalDesc.getSn(), autorizationPolicyDesc.getName() });
          }
        }
      }
      
      return principalEntryList;
    }
    
    private List<ResourceActionsEntry> createResourceActionsEntryList(List<TResourceActionsRef> resourceActionsDescList) throws PolicyStoreException {
      List<ResourceActionsEntry> resourceActionsEntryList = new java.util.ArrayList();
      for (TResourceActionsRef resourceActionsDesc : resourceActionsDescList) {
        resourceActionsEntryList.add(createResourceActionsEntry(resourceActionsDesc));
      }
      return resourceActionsEntryList;
    }
    
    private ResourceActionsEntry createResourceActionsEntry(TResourceActionsRef resourceActionsDesc) throws PolicyStoreException
    {
      oracle.security.jps.service.policystore.entitymanager.ResourceManager resourceManager = currentApplicationPolicy.getResourceManager();
      
      ResourceSearchQuery rQuery = new ResourceSearchQuery(java.util.Arrays.asList(new ResourceSearchQuery[] { new ResourceSearchQuery(oracle.security.jps.service.policystore.search.ResourceSearchQuery.SEARCH_PROPERTY.RESOURCE_TYPE, false, ComparatorType.EQUALITY, resourceActionsDesc.getResourceTypeName(), SearchQuery.MATCHER.EXACT), new ResourceSearchQuery(oracle.security.jps.service.policystore.search.ResourceSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, resourceActionsDesc.getResourceName(), SearchQuery.MATCHER.EXACT) }), false, false);
      





      List<ResourceEntry> rList = resourceManager.getResources(rQuery);
      if (rList.size() != 1) {
        throw new RuntimeException(rList.size() + " resources with name " + resourceActionsDesc.getResourceName() + " found");
      }
      
      List<String> actionList = new java.util.ArrayList();
      if (resourceActionsDesc.getActions().getAction() != null) {
        for (ru.otr.eb.utils.oes.modelimporter.model.TActionRef actionRef : resourceActionsDesc.getActions().getAction()) {
          actionList.add(actionRef.getName());
        }
      }
      return new oracle.security.jps.service.policystore.info.BasicResourceActionsEntry((ResourceEntry)rList.get(0), actionList);
    }
    
    private boolean isStoredPolicyEqualToImporting(PolicyEntry policyEntry, TAutorizationPolicyDesc autorizationPolicyDesc)
      throws PolicyStoreException, ParseException
    {
      boolean isModified = false;
      if (!policyEntry.getDisplayName().equals(autorizationPolicyDesc.getDisplayedName())) {
        OesModelImporter.log.info("Display name for policy '{}' changing from '{}' to '{}'", new Object[] { autorizationPolicyDesc.getName(), policyEntry.getDisplayName(), autorizationPolicyDesc.getDisplayedName() });
        policyEntry.setDisplayName(autorizationPolicyDesc.getDisplayedName());
        isModified = true;
      }
      
      final List<PrincipalEntry> storedPrincipalList = policyEntry.getPrincipals();
      List<PrincipalEntry> importingPrincipalList = getPrincipalEntryList(autorizationPolicyDesc);
      Collection<PrincipalEntry> newPrincipalList = CollectionUtils.select(importingPrincipalList, new org.apache.commons.collections.Predicate()
      {
        public boolean evaluate(final Object importingPrincipalEntryObject) {
          null == CollectionUtils.find(storedPrincipalList, new org.apache.commons.collections.Predicate()
          {
            public boolean evaluate(Object storedPrincipalEntryObject) {
              PrincipalEntry storedPrincipalEntry = (PrincipalEntry)storedPrincipalEntryObject;
              PrincipalEntry importingPrincipalEntry = (PrincipalEntry)importingPrincipalEntryObject;
              return (importingPrincipalEntry.getName().equals(storedPrincipalEntry.getName())) && (importingPrincipalEntry.getClassName().equals(storedPrincipalEntry.getClassName()));
            }
          });
        }
      });
      

      if (newPrincipalList.size() > 0) {
        Collection newPrincipalNames = CollectionUtils.transformedCollection(newPrincipalList, new org.apache.commons.collections.Transformer()
        {
          public Object transform(Object input) {
            return ((PrincipalEntry)input).getName();
          }
        });
        OesModelImporter.log.info("Principal list for policy '{}' changed. Principals '{}' added", autorizationPolicyDesc.getName(), java.util.Arrays.toString(newPrincipalNames.toArray()));
        
        for (PrincipalEntry principalEntry : newPrincipalList) {
          policyEntry.addPrincipal(principalEntry);
        }
        isModified = true;
      } else {
        OesModelImporter.log.debug("Principal list for policy '{}' is not changed", autorizationPolicyDesc.getName());
      }
      
      List<ResourceActionsEntry> storedResourceActionsList = policyEntry.getResourceActions();
      List<TResourceActionsRef> importingResourceActionsList = autorizationPolicyDesc.getResources().getResource();
      

      for (final TResourceActionsRef importingResourceActionsDesc : importingResourceActionsList) {
        ResourceActionsEntry resourceActionsEntry = (ResourceActionsEntry)CollectionUtils.find(storedResourceActionsList, new org.apache.commons.collections.Predicate()
        {
          public boolean evaluate(Object input) {
            ResourceActionsEntry storedResourceActionsEntry = (ResourceActionsEntry)input;
            return (storedResourceActionsEntry.getResourceType().equals(importingResourceActionsDesc.getResourceTypeName())) && (storedResourceActionsEntry.getResourceEntry().getName().equals(importingResourceActionsDesc.getResourceName()));
          }
        });
        

        if (resourceActionsEntry == null) {
          ResourceActionsEntry addingResourceActionsEntry = createResourceActionsEntry(importingResourceActionsDesc);
          OesModelImporter.log.info("New resource actions entry adding {resource: '{}/{}', actions: {}}", new Object[] { addingResourceActionsEntry.getResourceType(), addingResourceActionsEntry.getResourceEntry().getName(), java.util.Arrays.toString(addingResourceActionsEntry.getActions().toArray()) });
          storedResourceActionsList.add(addingResourceActionsEntry);
          isModified = true;
        } else {
          OesModelImporter.log.debug("Resource actions entry is found {resource: '{}/{}', actions: {}}", new Object[] { resourceActionsEntry.getResourceType(), resourceActionsEntry.getResourceEntry().getName(), java.util.Arrays.toString(resourceActionsEntry.getActions().toArray()) });
          java.util.Set<String> storedActionNames = resourceActionsEntry.getActions();
          List<String> importingActionNameList = new java.util.ArrayList();
          for (ru.otr.eb.utils.oes.modelimporter.model.TActionRef actionRef : importingResourceActionsDesc.getActions().getAction()) {
            importingActionNameList.add(actionRef.getName());
          }
          
          Collection newActionNames = CollectionUtils.subtract(importingActionNameList, storedActionNames);
          if (newActionNames.size() > 0) {
            OesModelImporter.log.info("New actions '{}' adding to resource '{}/{}' for policy '{}'", new Object[] { java.util.Arrays.toString(newActionNames.toArray()), importingResourceActionsDesc.getResourceTypeName(), importingResourceActionsDesc.getResourceName(), autorizationPolicyDesc.getName() });
            

            resourceActionsEntry.setActions(new java.util.ArrayList(CollectionUtils.union(storedActionNames, newActionNames)));
          }
        }
      }
      
      List<oracle.security.jps.service.policystore.info.PolicyRuleEntry> policyRuleEntryList = policyEntry.getRules();
      if (CollectionUtils.size(policyRuleEntryList) != 1) {
        throw new RuntimeException("Invalid count '" + CollectionUtils.size(policyRuleEntryList) + "' of policy rule entry in policy '" + policyEntry.getName() + "'");
      }
      
      oracle.security.jps.service.policystore.info.PolicyRuleEntry policyRuleEntry = (oracle.security.jps.service.policystore.info.PolicyRuleEntry)policyRuleEntryList.get(0);
      oracle.security.jps.service.policystore.info.RuleExpressionEntry storedCondition = policyRuleEntry.getCondition();
      oracle.security.jps.service.policystore.info.RuleExpressionEntry importingCondition = getRuleExpressionEntry(autorizationPolicyDesc.getCondition());
      if (storedCondition == null) {
        if (importingCondition == null) {
          OesModelImporter.log.debug("Stored and importing conditions are null");
        } else {
          OesModelImporter.log.info("Condition changing from null to '{}' for policy '{}'", importingCondition.toString(), policyEntry.getName());
          policyRuleEntry.setCondition(importingCondition);
          ru.otr.eb.utils.oes.common.ObligationUtils.replaceObligation(policyEntry, importingCondition, "ADDITIONAL_CONDITION", "CONDITION_IN_XML");
          isModified = true;
        }
      }
      else if (importingCondition == null) {
        OesModelImporter.log.info("Condition changing from '{}' to null", storedCondition.toString());
        policyRuleEntry.setCondition(null);
        ru.otr.eb.utils.oes.common.ObligationUtils.deleteObligation(policyEntry, "ADDITIONAL_CONDITION");
        isModified = true;
      } else {
        String importingConditionStr = importingCondition.toString();
        String storedConditionStr = storedCondition.toString();
        if (!importingConditionStr.equals(storedConditionStr)) {
          OesModelImporter.log.info("Conditiong changing from '{}' to '{}' for policy '{}'", new Object[] { storedConditionStr, importingConditionStr, policyEntry.getName() });
          policyRuleEntry.setCondition(importingCondition);
          ru.otr.eb.utils.oes.common.ObligationUtils.replaceObligation(policyEntry, importingCondition, "ADDITIONAL_CONDITION", "CONDITION_IN_XML");
          isModified = true;
        } else {
          OesModelImporter.log.info("Conditiong is not change value '{}'", storedConditionStr);
        }
      }
      
      return !isModified;
    }
    
    private oracle.security.jps.service.policystore.info.RuleExpressionEntry<oracle.security.jps.service.policystore.info.OpssBoolean> getRuleExpressionEntry(ru.otr.eb.utils.oes.modelimporter.model.TCondition conditionDesc) throws PolicyStoreException, ParseException {
      if (conditionDesc == null) {
        return null;
      }
      OesModelImporter.BuildOPSSConditionHandler conditionHandler = new OesModelImporter.BuildOPSSConditionHandler(OesModelImporter.this, currentApplicationPolicy.getExtensionManager());
      OesModelImporter.ConditionParser conditionParser = new OesModelImporter.ConditionParser(OesModelImporter.this, conditionDesc, conditionHandler);
      conditionParser.process();
      return conditionHandler.getResult();
    }
    
    private PrincipalEntry createUserPrincipalEntry(String cn) {
      return new oracle.security.jps.service.policystore.info.BasicPrincipalEntry("weblogic.security.principal.WLSUserImpl", cn);
    }
    
    private PrincipalEntry createGroupPrincipalEntry(String cn) {
      return new oracle.security.jps.service.policystore.info.BasicPrincipalEntry("weblogic.security.principal.WLSGroupImpl", cn);
    }
    
    private String getUserCNFromLdap(TUserPrincipalDesc userPrincipalDesc) {
      String userCN = null;
      
      if ((org.apache.commons.lang.StringUtils.isNotEmpty(userPrincipalDesc.getCn())) && (ldapDao.isUserExistByCn(userPrincipalDesc.getCn()))) {
        userCN = userPrincipalDesc.getCn();
      }
      
      if ((org.apache.commons.lang.StringUtils.isEmpty(userCN)) && (org.apache.commons.lang.StringUtils.isNotEmpty(userPrincipalDesc.getSn()))) {
        userCN = ldapDao.getCnBySN(userPrincipalDesc.getSn());
      }
      
      return userCN;
    }
  }
  
  private static String getCurrentTime() {
    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    return dateFormat.format(new java.util.Date());
  }
  
  private class ConditionParser {
    private ru.otr.eb.utils.oes.modelimporter.model.TCondition conditionDesc;
    private OesModelImporter.ConditionHandler conditionHandler;
    
    public ConditionParser(ru.otr.eb.utils.oes.modelimporter.model.TCondition conditionDesc, OesModelImporter.ConditionHandler conditionHandler) {
      this.conditionDesc = conditionDesc;
      this.conditionHandler = conditionHandler;
    }
    
    public void process() throws PolicyStoreException, ParseException {
      processFunction(conditionDesc.getF());
    }
    
    private void processFunction(ru.otr.eb.utils.oes.modelimporter.model.TFunction function) throws PolicyStoreException, ParseException {
      conditionHandler.startF(function);
      for (Object object : function.getParOrListOrF()) {
        if ((object instanceof TParameter)) {
          conditionHandler.addPar((TParameter)object);
        } else if ((object instanceof ru.otr.eb.utils.oes.modelimporter.model.TList)) {
          conditionHandler.startList();
          for (TParameter parameter : ((ru.otr.eb.utils.oes.modelimporter.model.TList)object).getPar()) {
            conditionHandler.addPar(parameter);
          }
          conditionHandler.finishList();
        } else if ((object instanceof ru.otr.eb.utils.oes.modelimporter.model.TFunction)) {
          processFunction((ru.otr.eb.utils.oes.modelimporter.model.TFunction)object);
        } else {
          throw new RuntimeException("Unknown object");
        }
      }
      conditionHandler.finishF();
    }
  }
  
  private static abstract interface ConditionHandler {
    public abstract void startF(ru.otr.eb.utils.oes.modelimporter.model.TFunction paramTFunction) throws PolicyStoreException;
    
    public abstract void finishF() throws PolicyStoreException;
    
    public abstract void addPar(TParameter paramTParameter) throws PolicyStoreException, ParseException;
    
    public abstract void startList() throws PolicyStoreException;
    
    public abstract void finishList() throws PolicyStoreException;
  }
  
  private class BuildOPSSConditionHandler implements OesModelImporter.ConditionHandler {
    private ExtensionManager xMgr;
    private java.util.Stack<Expression> expressionStack;
    private List currentList;
    private oracle.security.jps.service.policystore.info.RuleExpressionEntry<oracle.security.jps.service.policystore.info.OpssBoolean> result;
    
    public BuildOPSSConditionHandler(ExtensionManager xMgr) {
      expressionStack = new java.util.Stack();
      this.xMgr = xMgr;
    }
    
    public void startF(ru.otr.eb.utils.oes.modelimporter.model.TFunction function) throws PolicyStoreException
    {
      oracle.security.jps.service.policystore.info.FunctionEntry functionEntry = xMgr.getFunction(function.getNm().name());
      Expression expression = new Expression(functionEntry);
      if (!expressionStack.empty()) {
        ((Expression)expressionStack.peek()).addExpressionComponent(expression);
      }
      expressionStack.add(expression);
    }
    
    public void finishF() throws PolicyStoreException
    {
      Expression expression = (Expression)expressionStack.pop();
      if (expressionStack.isEmpty()) {
        result = new oracle.security.jps.service.policystore.info.BooleanExpressionEntry(expression);
      }
    }
    


    public void addPar(TParameter parameter)
      throws PolicyStoreException, ParseException
    {
      if (org.apache.commons.lang.StringUtils.isEmpty(parameter.getNm())) {
        DataType dataType;
        if (ru.otr.eb.utils.oes.modelimporter.model.TValueType.STR.equals(parameter.getTp())) {
          dataType = new oracle.security.jps.service.policystore.info.OpssString(parameter.getVal()); } else { DataType dataType;
          if (ru.otr.eb.utils.oes.modelimporter.model.TValueType.BOOL.equals(parameter.getTp())) {
            dataType = new oracle.security.jps.service.policystore.info.OpssBoolean(Boolean.valueOf(parameter.getVal()).booleanValue()); } else { DataType dataType;
            if (ru.otr.eb.utils.oes.modelimporter.model.TValueType.INT.equals(parameter.getTp())) {
              dataType = new oracle.security.jps.service.policystore.info.OpssInteger(Integer.valueOf(parameter.getVal()).intValue()); } else { DataType dataType;
              if (ru.otr.eb.utils.oes.modelimporter.model.TValueType.DBL.equals(parameter.getTp())) {
                dataType = new oracle.security.jps.service.policystore.info.OpssDouble(Double.parseDouble(parameter.getVal())); } else { DataType dataType;
                if (ru.otr.eb.utils.oes.modelimporter.model.TValueType.DATE.equals(parameter.getTp())) {
                  dataType = new oracle.security.jps.service.policystore.info.OpssDate(OesModelImporter.dateFormat.parse(parameter.getVal()));
                } else
                  throw new RuntimeException("Invalid attribute type '" + parameter.getTp() + "'");
              } } } }
        DataType dataType;
        if (currentList != null) {
          currentList.add(dataType);
        } else {
          ((Expression)expressionStack.peek()).addExpressionComponent(dataType);
        }
      }
      else {
        ((Expression)expressionStack.peek()).addExpressionComponent(xMgr.getAttribute(parameter.getNm()));
      }
    }
    
    public void startList()
      throws PolicyStoreException
    {
      currentList = new java.util.ArrayList();
    }
    
    public void finishList()
      throws PolicyStoreException
    {
      oracle.security.jps.service.policystore.info.ValueCollection valueCollection = new oracle.security.jps.service.policystore.info.ValueCollection();
      valueCollection.setValues(currentList);
      ((Expression)expressionStack.peek()).addExpressionComponent(valueCollection);
      currentList = null;
    }
    
    public oracle.security.jps.service.policystore.info.RuleExpressionEntry<oracle.security.jps.service.policystore.info.OpssBoolean> getResult() {
      return result;
    }
  }
}
