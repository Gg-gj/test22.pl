// 
// Decompiled by Procyon v0.5.30
// 

package ru.otr.eb.utils.oes.modelimporter;

import oracle.security.jps.service.policystore.info.ValueCollection;
import ru.otr.eb.utils.oes.modelimporter.model.TValueType;
import oracle.security.jps.service.policystore.info.BooleanExpressionEntry;
import oracle.security.jps.service.policystore.info.FunctionEntry;
import oracle.security.jps.service.policystore.info.ExpressionComponent;
import oracle.security.jps.service.policystore.info.Expression;
import java.util.Stack;
import ru.otr.eb.utils.oes.modelimporter.model.TList;
import ru.otr.eb.utils.oes.modelimporter.model.TParameter;
import ru.otr.eb.utils.oes.modelimporter.model.TFunction;
import oracle.security.jps.service.policystore.info.BasicPrincipalEntry;
import ru.otr.eb.utils.oes.common.ObligationUtils;
import oracle.security.jps.service.policystore.entitymanager.ResourceManager;
import oracle.security.jps.service.policystore.info.BasicResourceActionsEntry;
import ru.otr.eb.utils.oes.modelimporter.model.TActionRef;
import oracle.security.jps.service.policystore.info.resource.ResourceActionsEntry;
import ru.otr.eb.utils.oes.modelimporter.model.TResourceActionsRef;
import ru.otr.eb.utils.oes.modelimporter.model.TApplicationRoleName;
import oracle.security.jps.service.policystore.info.BasicObligationEntry;
import ru.otr.eb.utils.oes.modelimporter.model.TObligationAttribute;
import ru.otr.eb.utils.oes.modelimporter.model.TCondition;
import ru.otr.eb.utils.oes.modelimporter.model.TObligationConditionAttribute;
import oracle.security.jps.service.policystore.info.AttributeAssignment;
import ru.otr.eb.utils.oes.modelimporter.model.TObligation;
import oracle.security.jps.service.policystore.info.ObligationEntry;
import ru.otr.eb.utils.oes.modelimporter.model.TObligations;
import oracle.security.jps.service.policystore.info.CodeSourceEntry;
import oracle.security.jps.service.policystore.info.RuleExpressionEntry;
import oracle.security.jps.service.policystore.info.BasicPolicyRuleEntry;
import oracle.security.jps.service.policystore.info.PolicyRuleEntry;
import ru.otr.eb.utils.oes.modelimporter.model.TResourceTypeAttributeDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TActionDesc;
import oracle.security.jps.service.policystore.info.resource.ResourceEntry;
import oracle.security.jps.service.policystore.search.ResourceSearchQuery;
import java.util.Set;
import org.apache.commons.collections.Transformer;
import java.util.Arrays;
import ru.otr.eb.utils.oes.modelimporter.model.TResourceDesc;
import weblogic.security.principal.WLSGroupImpl;
import ru.otr.eb.utils.oes.modelimporter.model.TGroupPrincipalDesc;
import weblogic.security.principal.WLSUserImpl;
import java.security.Principal;
import org.apache.commons.collections.Predicate;
import ru.otr.eb.utils.oes.modelimporter.model.TUserPrincipalDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TRolePrincipalDesc;
import oracle.security.jps.service.policystore.info.PrincipalEntry;
import java.util.ArrayList;
import oracle.security.jps.service.policystore.info.OpssDouble;
import oracle.security.jps.service.policystore.info.OpssDate;
import oracle.security.jps.service.policystore.info.OpssString;
import oracle.security.jps.service.policystore.info.OpssInteger;
import oracle.security.jps.service.policystore.info.OpssBoolean;
import ru.otr.eb.utils.oes.modelimporter.model.TAttributeTypeEnum;
import oracle.security.jps.service.policystore.entitymanager.AppRoleManager;
import oracle.security.jps.service.policystore.info.DataType;
import oracle.security.jps.service.policystore.entitymanager.ExtensionManager;
import oracle.security.jps.service.policystore.entitymanager.ResourceTypeManager;
import java.util.List;
import oracle.security.jps.service.policystore.entitymanager.PolicyManager;
import oracle.security.jps.service.policystore.info.AppRoleEntry;
import oracle.security.jps.service.policystore.search.AppRoleSearchQuery;
import oracle.security.jps.service.policystore.info.AttributeEntry;
import oracle.security.jps.service.policystore.search.AttributeSearchQuery;
import oracle.security.jps.service.policystore.info.resource.ResourceTypeEntry;
import oracle.security.jps.service.policystore.search.ResourceTypeSearchQuery;
import oracle.security.jps.service.policystore.info.PolicyEntry;
import oracle.security.jps.search.SearchQuery;
import oracle.security.jps.service.policystore.search.ComparatorType;
import oracle.security.jps.service.policystore.search.PolicySearchQuery;
import ru.otr.eb.utils.oes.modelimporter.model.TAutorizationPolicyDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TResourceTypeDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TApplicationRoleDesc;
import ru.otr.eb.utils.oes.modelimporter.model.TAttributeDesc;
import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;
import oracle.security.jps.service.policystore.PolicyObjectNotFoundException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import java.util.Iterator;
import java.text.ParseException;
import oracle.security.jps.service.policystore.PolicyStoreException;
import ru.otr.eb.utils.oes.modelimporter.model.TApplicationDesc;
import oracle.security.jps.service.policystore.ApplicationPolicy;
import org.slf4j.LoggerFactory;
import java.util.Date;
import oracle.security.jps.service.policystore.BindingPolicyStore;
import ru.otr.eb.utils.oes.modelimporter.model.TPolicyStoreDesc;
import oracle.security.jps.JpsException;
import ru.otr.eb.utils.oes.modelimporter.actions.ActionsNamesService;
import ru.otr.eb.utils.oes.common.OESPdpPolicyDistributor;
import ru.otr.eb.utils.oes.OesPolicyStoreConnector;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;

public class OesModelImporter
{
    private static Logger log;
    private static SimpleDateFormat dateFormat;
    private ModelLoader modelLoader;
    private OesPolicyStoreConnector oesPolicyStoreConnector;
    private LdapDao ldapDao;
    private OESPdpPolicyDistributor oesPdpPolicyDistributor;
    private ActionsNamesService actionsNamesService;
    private ImportDataInfoProvider importDataInfoProvider;
    private boolean policyStoreClearBeforeImport;
    private String[] importingApplications;
    private boolean registerOnlyResources;
    private boolean policyAddNotExistentPrincipals;
    private boolean distributeOnly;
    
    public void doImport() {
        final TPolicyStoreDesc policyStoreDesc = this.modelLoader.getPolicyStoreDesc();
        if (policyStoreDesc == null) {
            OesModelImporter.log.error("Policy store desc is not initialized");
            return;
        }
        BindingPolicyStore bindingPolicyStore;
        try {
            bindingPolicyStore = this.oesPolicyStoreConnector.connectPolicyStore();
        }
        catch (JpsException e) {
            OesModelImporter.log.error("Policy store connection error", (Throwable)e);
            return;
        }
        final Importer importer = new Importer(policyStoreDesc, bindingPolicyStore);
        try {
            importer.doImport();
        }
        catch (Exception e2) {
            OesModelImporter.log.error("Model import error", (Throwable)e2);
        }
    }
    
    public void setPolicyStoreClearBeforeImport(final boolean policyStoreClearBeforeImport) {
        this.policyStoreClearBeforeImport = policyStoreClearBeforeImport;
    }
    
    public void setLdapDao(final LdapDao ldapDao) {
        this.ldapDao = ldapDao;
    }
    
    public void setModelLoader(final ModelLoader modelLoader) {
        this.modelLoader = modelLoader;
    }
    
    public void setOesPolicyStoreConnector(final OesPolicyStoreConnector oesPolicyStoreConnector) {
        this.oesPolicyStoreConnector = oesPolicyStoreConnector;
    }
    
    public void setOesPdpPolicyDistributor(final OESPdpPolicyDistributor oesPdpPolicyDistributor) {
        this.oesPdpPolicyDistributor = oesPdpPolicyDistributor;
    }
    
    public void setImportingApplications(final String[] importingApplications) {
        this.importingApplications = importingApplications;
    }
    
    public void setRegisterOnlyResources(final boolean registerOnlyResources) {
        this.registerOnlyResources = registerOnlyResources;
    }
    
    public void setPolicyAddNotExistentPrincipals(final boolean policyAddNotExistentPrincipals) {
        this.policyAddNotExistentPrincipals = policyAddNotExistentPrincipals;
    }
    
    public void setActionsNamesService(final ActionsNamesService actionsNamesService) {
        this.actionsNamesService = actionsNamesService;
    }
    
    public void setDistributeOnly(final boolean distributeOnly) {
        this.distributeOnly = distributeOnly;
    }
    
    public void setImportDataInfoProvider(final ImportDataInfoProvider importDataInfoProvider) {
        this.importDataInfoProvider = importDataInfoProvider;
    }
    
    private static String getCurrentTime() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }
    
    static {
        OesModelImporter.log = LoggerFactory.getLogger((Class)OesModelImporter.class);
        OesModelImporter.dateFormat = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
    }
    
    private class Importer
    {
        private TPolicyStoreDesc policyStoreDesc;
        private BindingPolicyStore bindingPolicyStore;
        private ApplicationPolicy currentApplicationPolicy;
        
        private Importer(final TPolicyStoreDesc policyStoreDesc, final BindingPolicyStore bindingPolicyStore) {
            this.policyStoreDesc = policyStoreDesc;
            this.bindingPolicyStore = bindingPolicyStore;
        }
        
        public void doImport() throws PolicyStoreException, ParseException {
            OesModelImporter.log.info("START IMPORT");
            for (final TApplicationDesc applicationDesc : this.policyStoreDesc.getApplication()) {
                try {
                    this.doImportApplication(applicationDesc);
                    this.addInfoToApplicationDescription("Successfull finish time: " + getCurrentTime());
                }
                catch (PolicyStoreException e) {
                    this.addInfoToApplicationDescription("Error finish time: " + getCurrentTime() + ", error: " + e.getMessage());
                    throw e;
                }
                catch (ParseException e2) {
                    this.addInfoToApplicationDescription("Error finish time: " + getCurrentTime() + ", error: " + e2.getMessage());
                    throw e2;
                }
                catch (RuntimeException e3) {
                    this.addInfoToApplicationDescription("Error finish time: " + getCurrentTime() + ", error: " + e3.getMessage());
                    throw e3;
                }
            }
            OesModelImporter.log.info("FINISH IMPORT");
        }
        
        private void addInfoToApplicationDescription(final String info) throws PolicyStoreException {
            if (this.currentApplicationPolicy == null) {
                return;
            }
            String applicationPolicyDescription = this.currentApplicationPolicy.getApplicationDescription();
            if (applicationPolicyDescription.length() + info.length() + 1 < 3000) {
                applicationPolicyDescription = applicationPolicyDescription + "\n" + info;
            }
            else {
                applicationPolicyDescription = StringUtils.substring(applicationPolicyDescription, 3000 - info.length() - 1) + "\n" + info;
            }
            this.currentApplicationPolicy.setDescription(applicationPolicyDescription);
            OesModelImporter.log.debug("Starting updating application description");
            this.bindingPolicyStore.modifyApplicationPolicy(this.currentApplicationPolicy);
            OesModelImporter.log.debug("Finish updating application description");
        }
        
        public void doImportApplication(final TApplicationDesc applicationDesc) throws PolicyStoreException, ParseException {
            OesModelImporter.log.info("================================================================================");
            if (ArrayUtils.isNotEmpty((Object[])OesModelImporter.this.importingApplications) && !ArrayUtils.contains((Object[])OesModelImporter.this.importingApplications, (Object)applicationDesc.getName())) {
                OesModelImporter.log.info("Application '{}' is not in importing list {}, skipping", (Object)applicationDesc.getName(), (Object)ArrayUtils.toString((Object)OesModelImporter.this.importingApplications));
                return;
            }
            OesModelImporter.log.info("Processing application '{}'", (Object)applicationDesc.getName());
            final String importDataInfo = OesModelImporter.this.importDataInfoProvider.getImportDataInfo();
            try {
                this.currentApplicationPolicy = this.bindingPolicyStore.getApplicationPolicy(applicationDesc.getName());
                OesModelImporter.log.debug("Application '{}' is found", (Object)applicationDesc.getName());
                this.addInfoToApplicationDescription("Start time: " + getCurrentTime() + "\n" + importDataInfo);
                if (OesModelImporter.this.distributeOnly) {
                    OesModelImporter.log.debug("distributeOnly property is true, starting distributing");
                    OesModelImporter.this.oesPdpPolicyDistributor.doDistribute(this.currentApplicationPolicy);
                    return;
                }
                if (OesModelImporter.this.policyStoreClearBeforeImport) {
                    OesModelImporter.log.info("Start clearing application '{}'", (Object)applicationDesc.getName());
                    this.clearApplication();
                    OesModelImporter.log.info("Finish clearing application '{}'", (Object)applicationDesc.getName());
                }
            }
            catch (PolicyObjectNotFoundException e) {
                OesModelImporter.log.info("Application '{}' is not found. Creating...", (Object)applicationDesc.getName());
                this.currentApplicationPolicy = this.bindingPolicyStore.createApplicationPolicy(applicationDesc.getName(), applicationDesc.getDisplayedName(), importDataInfo);
            }
            OesModelImporter.log.debug("Starting import model for application '{}'", (Object)applicationDesc.getName());
            if (applicationDesc.getAttributeList() != null && CollectionUtils.isNotEmpty((Collection)applicationDesc.getAttributeList().getAttribute())) {
                OesModelImporter.log.debug("Starting import attributes");
                for (final TAttributeDesc attributeDesc : applicationDesc.getAttributeList().getAttribute()) {
                    this.doImportAttribute(attributeDesc);
                }
            }
            else {
                OesModelImporter.log.debug("No attributes are defined");
            }
            if (OesModelImporter.this.registerOnlyResources) {
                OesModelImporter.log.debug("Ignoring roles, because of setting -or=true");
            }
            else if (applicationDesc.getRoleList() != null && CollectionUtils.isNotEmpty((Collection)applicationDesc.getRoleList().getRole())) {
                OesModelImporter.log.debug("Starting import application roles");
                for (final TApplicationRoleDesc applicationRoleDesc : applicationDesc.getRoleList().getRole()) {
                    this.doImportApplicationRole(applicationRoleDesc);
                }
            }
            else {
                OesModelImporter.log.debug("No application roles are defined");
            }
            if (OesModelImporter.this.registerOnlyResources) {
                OesModelImporter.log.debug("Ignoring inherited roles, because of setting -or=true");
            }
            else if (applicationDesc.getRoleList() != null && CollectionUtils.isNotEmpty((Collection)applicationDesc.getRoleList().getRole())) {
                OesModelImporter.log.debug("Starting granting inherited application roles");
                for (final TApplicationRoleDesc applicationRoleDesc : applicationDesc.getRoleList().getRole()) {
                    this.doGrantInheretedApplicationRole(applicationRoleDesc);
                }
            }
            if (applicationDesc.getResourceTypeList() != null && CollectionUtils.isNotEmpty((Collection)applicationDesc.getResourceTypeList().getResourceType())) {
                OesModelImporter.log.debug("Starting import application resource types");
                for (final TResourceTypeDesc resourceTypeDesc : applicationDesc.getResourceTypeList().getResourceType()) {
                    this.doImportResourceType(resourceTypeDesc);
                }
            }
            else {
                OesModelImporter.log.debug("No resource types are defined");
            }
            if (OesModelImporter.this.registerOnlyResources) {
                OesModelImporter.log.debug("Ignoring policies, because of setting -or=true");
            }
            else if (applicationDesc.getPolicyList() != null && CollectionUtils.isNotEmpty((Collection)applicationDesc.getPolicyList().getPolicy())) {
                OesModelImporter.log.debug("Starting import application policies");
                for (final TAutorizationPolicyDesc autorizationPolicyDesc : applicationDesc.getPolicyList().getPolicy()) {
                    this.doImportAutorizationPolicy(autorizationPolicyDesc);
                }
            }
            else {
                OesModelImporter.log.debug("No policies are defined");
            }
            OesModelImporter.this.oesPdpPolicyDistributor.doDistribute(this.currentApplicationPolicy);
        }
        
        private void clearApplication() throws PolicyStoreException {
            if (OesModelImporter.this.registerOnlyResources) {
                OesModelImporter.log.debug("Policies are not deleting, because of setting -or=true");
            }
            else {
                final PolicyManager policyManager = this.currentApplicationPolicy.getPolicyManager();
                final PolicySearchQuery policySearchQuery = new PolicySearchQuery(PolicySearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, (Object)"-", SearchQuery.MATCHER.ANY);
                final List<PolicyEntry> policyEntryList = (List<PolicyEntry>)policyManager.getPolicies(policySearchQuery);
                if (CollectionUtils.isNotEmpty((Collection)policyEntryList)) {
                    for (final PolicyEntry policyEntry : policyEntryList) {
                        OesModelImporter.log.info("Deleting policy '{}'", (Object)policyEntry.getName());
                        policyManager.deletePolicy(policyEntry.getName());
                    }
                }
                else {
                    OesModelImporter.log.debug("No policy is found for delete");
                }
            }
            final ResourceTypeManager resourceTypeManager = this.currentApplicationPolicy.getResourceTypeManager();
            final ResourceTypeSearchQuery resourceTypeSearchQuery = new ResourceTypeSearchQuery(ResourceTypeSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
            final List<ResourceTypeEntry> resourceTypeEntryList = (List<ResourceTypeEntry>)resourceTypeManager.getResourceTypes(resourceTypeSearchQuery);
            if (CollectionUtils.isNotEmpty((Collection)resourceTypeEntryList)) {
                for (final ResourceTypeEntry resourceTypeEntry : resourceTypeEntryList) {
                    OesModelImporter.log.info("Deleting resource type '{}'", (Object)resourceTypeEntry.getName());
                    resourceTypeManager.deleteResourceType(resourceTypeEntry.getName(), true);
                }
            }
            else {
                OesModelImporter.log.debug("No resource type is found for delete");
            }
            final ExtensionManager extensionManager = this.currentApplicationPolicy.getExtensionManager();
            final AttributeSearchQuery attributeSearchQuery = new AttributeSearchQuery(AttributeSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
            final List<AttributeEntry<? extends DataType>> attributes = (List<AttributeEntry<? extends DataType>>)extensionManager.getAttributes(attributeSearchQuery);
            if (CollectionUtils.isNotEmpty((Collection)attributes)) {
                for (final AttributeEntry<? extends DataType> attributeEntry : attributes) {
                    if (!attributeEntry.isBuiltInAttribute()) {
                        OesModelImporter.log.info("Deleting attribute '{}'", (Object)attributeEntry.getName());
                        extensionManager.deleteAttribute(attributeEntry.getName(), false);
                    }
                }
            }
            else {
                OesModelImporter.log.debug("No attribute is found for delete");
            }
            if (OesModelImporter.this.registerOnlyResources) {
                OesModelImporter.log.debug("Policies are not deleting, because of setting -or=true");
            }
            else {
                final AppRoleManager appRoleManager = this.currentApplicationPolicy.getAppRoleManager();
                final AppRoleSearchQuery appRoleSearchQuery = new AppRoleSearchQuery(AppRoleSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
                final List<AppRoleEntry> appRoleEntryList = (List<AppRoleEntry>)appRoleManager.getAppRoles(appRoleSearchQuery);
                if (CollectionUtils.isNotEmpty((Collection)appRoleEntryList)) {
                    for (final AppRoleEntry appRoleEntry : appRoleEntryList) {
                        OesModelImporter.log.info("Deleting application role '{}'", (Object)appRoleEntry.getName());
                        appRoleManager.deleteAppRole(appRoleEntry.getName(), true);
                    }
                }
                else {
                    OesModelImporter.log.debug("No application role is found for delete");
                }
            }
            OesModelImporter.this.actionsNamesService.deleteAll(this.currentApplicationPolicy.getApplicationName());
        }
        
        private void doImportAttribute(final TAttributeDesc attributeDesc) throws PolicyStoreException {
            final ExtensionManager extensionManager = this.currentApplicationPolicy.getExtensionManager();
            try {
                extensionManager.getAttribute(attributeDesc.getName());
                OesModelImporter.log.debug("Attribute '{}' is found", (Object)attributeDesc.getName());
            }
            catch (PolicyObjectNotFoundException e) {
                OesModelImporter.log.info("Attribute '{}' is not found. Creating...", (Object)attributeDesc.getName());
                extensionManager.createAttribute(attributeDesc.getName(), attributeDesc.getDisplayedName(), attributeDesc.getDisplayedName(), (Class)this.getDataType(attributeDesc.getType()), AttributeEntry.AttributeCategory.RESOURCE, true);
            }
        }
        
        private Class<? extends DataType> getDataType(final TAttributeTypeEnum attributeType) {
            if (TAttributeTypeEnum.BOOLEAN.equals(attributeType)) {
                return (Class<? extends DataType>)OpssBoolean.class;
            }
            if (TAttributeTypeEnum.INTEGER.equals(attributeType)) {
                return (Class<? extends DataType>)OpssInteger.class;
            }
            if (TAttributeTypeEnum.STRING.equals(attributeType)) {
                return (Class<? extends DataType>)OpssString.class;
            }
            if (TAttributeTypeEnum.DATE.equals(attributeType)) {
                return (Class<? extends DataType>)OpssDate.class;
            }
            if (TAttributeTypeEnum.DOUBLE.equals(attributeType)) {
                return (Class<? extends DataType>)OpssDouble.class;
            }
            throw new RuntimeException("Invalid attribute type '" + attributeType + "'");
        }
        
        private void doGrantInheretedApplicationRole(final TApplicationRoleDesc applicationRoleDesc) throws PolicyStoreException {
            final List<AppRoleEntry> appRoleEntryList = this.getAppRoleEntryList(applicationRoleDesc.getName());
            if (appRoleEntryList.size() > 1) {
                OesModelImporter.log.error("{} application role '{}' found. Exit", (Object)appRoleEntryList.size(), (Object)applicationRoleDesc.getName());
                return;
            }
            final AppRoleEntry appRoleEntry = appRoleEntryList.get(0);
            if (applicationRoleDesc.getRoles() != null && CollectionUtils.isNotEmpty((Collection)applicationRoleDesc.getRoles().getRole())) {
                final AppRoleManager appRoleManager = this.currentApplicationPolicy.getAppRoleManager();
                final List<PrincipalEntry> rolesForGrantList = new ArrayList<PrincipalEntry>();
                for (final TRolePrincipalDesc inheritedRoleFromModel : applicationRoleDesc.getRoles().getRole()) {
                    final List<AppRoleEntry> inheridedRoleFromDBList = this.getAppRoleEntryList(inheritedRoleFromModel.getName());
                    if (inheridedRoleFromDBList.size() > 1) {
                        OesModelImporter.log.error("{} inherited application role '{}' found. Exit", (Object)inheridedRoleFromDBList.size(), (Object)inheritedRoleFromModel.getName());
                    }
                    else if (inheridedRoleFromDBList.size() == 0) {
                        OesModelImporter.log.error("Inherited application role '{}' not found for role {}. Exit", (Object)inheritedRoleFromModel.getName(), (Object)applicationRoleDesc.getName());
                    }
                    else {
                        rolesForGrantList.add((PrincipalEntry)inheridedRoleFromDBList.get(0));
                    }
                }
                OesModelImporter.log.info("Adding inherited roles '{}' for application role '{}'", (Object)rolesForGrantList, (Object)applicationRoleDesc.getName());
                appRoleManager.grantAppRole(appRoleEntry, (List)rolesForGrantList);
                appRoleManager.modifyAppRole(appRoleEntry);
            }
            else {
                OesModelImporter.log.debug("Application role '{}' has no inherited roles", (Object)applicationRoleDesc.getName());
            }
        }
        
        private List<AppRoleEntry> getAppRoleEntryList(final String appRoleName) throws PolicyStoreException {
            final AppRoleManager appRoleManager = this.currentApplicationPolicy.getAppRoleManager();
            final AppRoleSearchQuery query = new AppRoleSearchQuery(AppRoleSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, appRoleName, SearchQuery.MATCHER.EXACT);
            return (List<AppRoleEntry>)appRoleManager.getAppRoles(query);
        }
        
        private void doImportApplicationRole(final TApplicationRoleDesc applicationRoleDesc) throws PolicyStoreException {
            final AppRoleManager appRoleManager = this.currentApplicationPolicy.getAppRoleManager();
            final AppRoleSearchQuery query = new AppRoleSearchQuery(AppRoleSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, applicationRoleDesc.getName(), SearchQuery.MATCHER.EXACT);
            final List<AppRoleEntry> appRoleEntryList = (List<AppRoleEntry>)appRoleManager.getAppRoles(query);
            if (appRoleEntryList.size() > 1) {
                OesModelImporter.log.error("{} application role '{}' found. Exit", (Object)appRoleEntryList.size(), (Object)applicationRoleDesc.getName());
                return;
            }
            if (appRoleEntryList.size() == 0) {
                OesModelImporter.log.info("Application role '{}' is not found. Creating..", (Object)applicationRoleDesc.getName());
                final AppRoleEntry appRoleEntry = appRoleManager.createAppRole(applicationRoleDesc.getName(), applicationRoleDesc.getDisplayedName(), applicationRoleDesc.getDisplayedName());
                if (!this.isStoredApplicationRoleEqualToImporting(appRoleEntry, applicationRoleDesc)) {
                    OesModelImporter.log.info("Created application role '{}' is modified by principals. Updating in store...", (Object)applicationRoleDesc.getName());
                    appRoleManager.modifyAppRole(appRoleEntry);
                }
            }
            else {
                OesModelImporter.log.debug("Application role '{}' is found", (Object)applicationRoleDesc.getName());
                final AppRoleEntry appRoleEntry = appRoleEntryList.get(0);
                if (!this.isStoredApplicationRoleEqualToImporting(appRoleEntry, applicationRoleDesc)) {
                    OesModelImporter.log.info("Application role '{}' is changing. Updating in store...", (Object)applicationRoleDesc.getName());
                    appRoleManager.modifyAppRole(appRoleEntry);
                }
            }
        }
        
        private boolean isStoredApplicationRoleEqualToImporting(final AppRoleEntry appRoleEntry, final TApplicationRoleDesc applicationRoleDesc) throws PolicyStoreException {
            boolean isModified = false;
            if (!appRoleEntry.getDisplayName().equals(applicationRoleDesc.getDisplayedName())) {
                OesModelImporter.log.info("Display name for application role '{}' changing from '{}' to '{}'", new Object[] { applicationRoleDesc.getName(), appRoleEntry.getDisplayName(), applicationRoleDesc.getDisplayedName() });
                appRoleEntry.setDisplayName(applicationRoleDesc.getDisplayedName());
                isModified = true;
            }
            final List<Principal> currentApplicationRolePrincipals = (List<Principal>)this.currentApplicationPolicy.getAppRolesMembers(applicationRoleDesc.getName());
            if (applicationRoleDesc.getUsers() != null) {
                for (final TUserPrincipalDesc userPrincipalDesc : applicationRoleDesc.getUsers().getUser()) {
                    String userCN = this.getUserCNFromLdap(userPrincipalDesc);
                    if (StringUtils.isEmpty(userCN)) {
                        if (!OesModelImporter.this.policyAddNotExistentPrincipals) {
                            OesModelImporter.log.error("User {cn: '{}', sn: '{}'} is not found in ldap, skip this user", (Object)userPrincipalDesc.getCn(), (Object)userPrincipalDesc.getSn());
                            continue;
                        }
                        userCN = userPrincipalDesc.getSn();
                        if (StringUtils.isEmpty(userCN)) {
                            userCN = userPrincipalDesc.getCn();
                        }
                        OesModelImporter.log.debug("User {cn: '{}', sn: '{}'} is not found in ldap, but not skip this user", (Object)userPrincipalDesc.getCn(), (Object)userPrincipalDesc.getSn());
                    }
                    else {
                        OesModelImporter.log.debug("User {cn: '{}', sn: '{}'} is found in ldap", (Object)userPrincipalDesc.getCn(), (Object)userPrincipalDesc.getSn());
                    }
                    final String finalUserCN = userCN;
                    if (CollectionUtils.select((Collection)currentApplicationRolePrincipals, (Predicate)new Predicate() {
                        public boolean evaluate(final Object object) {
                            final Principal principal = (Principal)object;
                            return principal.getClass().equals(WLSUserImpl.class) && principal.getName().equals(finalUserCN);
                        }
                    }).size() == 0) {
                        OesModelImporter.log.info("User {cn: '{}', sn: '{}'} is not linked to application role '{}'. Linking...", new Object[] { userCN, userPrincipalDesc.getSn(), applicationRoleDesc.getName() });
                        final PrincipalEntry pe = this.createUserPrincipalEntry(userCN);
                        this.currentApplicationPolicy.addPrincipalToAppRole(pe, applicationRoleDesc.getName());
                        isModified = true;
                    }
                    else {
                        OesModelImporter.log.debug("User {cn:'{}', sn: '{}'} is already linked to application role '{}'", new Object[] { userCN, userPrincipalDesc.getSn(), applicationRoleDesc.getName() });
                    }
                }
            }
            if (applicationRoleDesc.getGroups() != null) {
                for (final TGroupPrincipalDesc groupPrincipalDesc : applicationRoleDesc.getGroups().getGroup()) {
                    final String groupCn = groupPrincipalDesc.getCn();
                    if (!OesModelImporter.this.ldapDao.isGroupExistByCn(groupCn)) {
                        if (!OesModelImporter.this.policyAddNotExistentPrincipals) {
                            OesModelImporter.log.error("Group '{}' is not found in ldap,  skip this group", (Object)groupPrincipalDesc.getCn(), (Object)applicationRoleDesc.getName());
                            continue;
                        }
                        OesModelImporter.log.info("Group '{}' is not found in ldap,  but not skip this group", (Object)groupPrincipalDesc.getCn(), (Object)applicationRoleDesc.getName());
                    }
                    else {
                        OesModelImporter.log.debug("Group '{}' is found in ldap", (Object)groupCn);
                    }
                    if (CollectionUtils.select((Collection)currentApplicationRolePrincipals, (Predicate)new Predicate() {
                        public boolean evaluate(final Object object) {
                            final Principal principal = (Principal)object;
                            return principal.getClass().equals(WLSGroupImpl.class) && principal.getName().equals(groupCn);
                        }
                    }).size() == 0) {
                        OesModelImporter.log.info("Group {name:'{}'} is not linked to application role '{}'. Linking...", (Object)groupCn, (Object)applicationRoleDesc.getName());
                        final PrincipalEntry pe2 = this.createGroupPrincipalEntry(groupCn);
                        this.currentApplicationPolicy.addPrincipalToAppRole(pe2, applicationRoleDesc.getName());
                        isModified = true;
                    }
                    else {
                        OesModelImporter.log.debug("Group {name: '{}'} is already linked to application role '{}'", (Object)groupCn, (Object)applicationRoleDesc.getName());
                    }
                }
            }
            return !isModified;
        }
        
        private void doImportResourceType(final TResourceTypeDesc resourceTypeDesc) throws PolicyStoreException {
            final ResourceTypeManager resourceTypeManager = this.currentApplicationPolicy.getResourceTypeManager();
            final ResourceTypeSearchQuery rtQuery = new ResourceTypeSearchQuery(ResourceTypeSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, resourceTypeDesc.getName(), SearchQuery.MATCHER.EXACT);
            final List<ResourceTypeEntry> resourceTypeEntryList = (List<ResourceTypeEntry>)resourceTypeManager.getResourceTypes(rtQuery);
            if (resourceTypeEntryList.size() > 1) {
                OesModelImporter.log.error("{} resource type '{}' is found. Exit", (Object)resourceTypeEntryList.size(), (Object)resourceTypeDesc.getName());
                return;
            }
            ResourceTypeEntry resourceTypeEntry = null;
            if (resourceTypeEntryList.size() == 0) {
                OesModelImporter.log.info("Resource type '{}' is not found. Creating..", (Object)resourceTypeDesc.getName());
                resourceTypeEntry = resourceTypeManager.createResourceType(resourceTypeDesc.getName(), resourceTypeDesc.getDisplayedName(), resourceTypeDesc.getDisplayedName(), (List)this.getActionNameList(resourceTypeDesc), (List)this.getAttributeList(resourceTypeDesc), ",");
            }
            else {
                OesModelImporter.log.debug("Resource type '{}' is found", (Object)resourceTypeDesc.getName());
                resourceTypeEntry = resourceTypeEntryList.get(0);
                if (!this.isStoredResourceTypeEqualToImporting(resourceTypeEntry, resourceTypeDesc)) {
                    OesModelImporter.log.info("Resource type '{}' is changing. Updating resource type in store...", (Object)resourceTypeDesc.getName());
                    resourceTypeManager.modifyResourceType(resourceTypeEntry);
                }
            }
            if (resourceTypeDesc.getResources() != null && CollectionUtils.isNotEmpty((Collection)resourceTypeDesc.getResources().getResource())) {
                OesModelImporter.log.debug("Starting import resources for resource type '{}'", (Object)resourceTypeDesc.getName());
                for (final TResourceDesc resourceDesc : resourceTypeDesc.getResources().getResource()) {
                    this.doImportResource(resourceTypeEntry, resourceDesc);
                }
            }
        }
        
        private boolean isStoredResourceTypeEqualToImporting(final ResourceTypeEntry resourceTypeEntry, final TResourceTypeDesc resourceTypeDesc) throws PolicyStoreException {
            boolean isModified = false;
            if (!"ANY".equals(resourceTypeEntry.getAllAction())) {
                OesModelImporter.log.info("All action name is not 'ANY'. Need to change to 'ANY' current value '{}'", (Object)resourceTypeEntry.getAllAction());
                resourceTypeEntry.setAllAction("ANY");
                isModified = true;
            }
            if (!resourceTypeEntry.getDisplayName().equals(resourceTypeDesc.getDisplayedName())) {
                OesModelImporter.log.info("Display name for resource type '{}' changing from '{}' to '{}'", new Object[] { resourceTypeDesc.getName(), resourceTypeEntry.getDisplayName(), resourceTypeDesc.getDisplayedName() });
                resourceTypeEntry.setDisplayName(resourceTypeDesc.getDisplayedName());
                isModified = true;
            }
            final Set<String> storedActionSet = (Set<String>)resourceTypeEntry.getActions();
            final List<String> importingActionSet = this.getActionNameList(resourceTypeDesc);
            final Collection<String> newActionList = (Collection<String>)CollectionUtils.subtract((Collection)importingActionSet, (Collection)storedActionSet);
            if (newActionList.size() > 0) {
                OesModelImporter.log.info("Action list for resource type '{}' changed. New actions '{}' added", (Object)resourceTypeDesc.getName(), (Object)Arrays.toString(newActionList.toArray()));
                storedActionSet.addAll(newActionList);
                isModified = true;
            }
            else {
                OesModelImporter.log.debug("Action list for resource type '{}' is not changed. Actual list '{}'", (Object)resourceTypeDesc.getName(), (Object)Arrays.toString(storedActionSet.toArray()));
            }
            final List<AttributeEntry<? extends DataType>> storedAttributeList = (List<AttributeEntry<? extends DataType>>)resourceTypeEntry.getValidResourceAttributes();
            final List<AttributeEntry<? extends DataType>> importingAttributeList = this.getAttributeList(resourceTypeDesc);
            final Collection<AttributeEntry<? extends DataType>> newAttributeList = (Collection<AttributeEntry<? extends DataType>>)CollectionUtils.select((Collection)importingAttributeList, (Predicate)new Predicate() {
                public boolean evaluate(final Object importingAttributeEntry) {
                    return null == CollectionUtils.find((Collection)storedAttributeList, (Predicate)new Predicate() {
                        public boolean evaluate(final Object storedAttributeEntry) {
                            return ((AttributeEntry)storedAttributeEntry).getName().equals(((AttributeEntry)importingAttributeEntry).getName());
                        }
                    });
                }
            });
            if (newAttributeList.size() > 0) {
                final Collection newAttributeNames = CollectionUtils.transformedCollection((Collection)newAttributeList, (Transformer)new Transformer() {
                    public Object transform(final Object input) {
                        return ((AttributeEntry)input).getName();
                    }
                });
                OesModelImporter.log.info("Attribute list for resource type '{}' changed. New actions '{}' added", (Object)resourceTypeDesc.getName(), (Object)Arrays.toString(newAttributeNames.toArray()));
                for (final AttributeEntry<? extends DataType> attributeEntry : newAttributeList) {
                    resourceTypeEntry.addValidResourceAttribute((AttributeEntry)attributeEntry);
                }
                isModified = true;
            }
            else {
                OesModelImporter.log.debug("Attribute list for resource type '{}' is not changed", (Object)resourceTypeDesc.getName());
            }
            return !isModified;
        }
        
        private void doImportResource(final ResourceTypeEntry resourceTypeEntry, final TResourceDesc resourceDesc) throws PolicyStoreException {
            final ResourceSearchQuery resourceTypeQuery = new ResourceSearchQuery(ResourceSearchQuery.SEARCH_PROPERTY.RESOURCE_TYPE, false, ComparatorType.EQUALITY, (Object)resourceTypeEntry.getName(), SearchQuery.MATCHER.EXACT);
            final ResourceSearchQuery resourceNameQuery = new ResourceSearchQuery(ResourceSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, (Object)resourceDesc.getName(), SearchQuery.MATCHER.EXACT);
            final ResourceSearchQuery query = new ResourceSearchQuery((List)Arrays.asList(resourceTypeQuery, resourceNameQuery), false, false);
            final List<ResourceEntry> resourceEntryList = (List<ResourceEntry>)this.currentApplicationPolicy.getResourceManager().getResources(query);
            if (resourceEntryList.size() == 0) {
                OesModelImporter.log.info("Resource '{}/{}' is not found. Creating... ", (Object)resourceTypeEntry.getName(), (Object)resourceDesc.getName());
                this.currentApplicationPolicy.getResourceManager().createResource(resourceDesc.getName(), resourceDesc.getDisplayedName(), resourceDesc.getDisplayedName(), resourceTypeEntry, (List)null);
            }
            else {
                OesModelImporter.log.debug("Resource '{}/{}' is already registered", (Object)resourceTypeEntry.getName(), (Object)resourceDesc.getName());
                final ResourceEntry resourceEntry = resourceEntryList.get(0);
                if (!this.isStoredResourceEqualToImporting(resourceEntry, resourceDesc, resourceTypeEntry)) {
                    OesModelImporter.log.info("Resource '{}/{}' is changed. Updating resource in store...", (Object)resourceTypeEntry.getName(), (Object)resourceDesc.getName());
                    this.currentApplicationPolicy.getResourceManager().modifyResource(resourceEntry);
                }
            }
        }
        
        private boolean isStoredResourceEqualToImporting(final ResourceEntry resourceEntry, final TResourceDesc resourceDesc, final ResourceTypeEntry resourceTypeEntry) throws PolicyStoreException {
            boolean isModified = false;
            if (!resourceEntry.getDisplayName().equals(resourceDesc.getDisplayedName())) {
                OesModelImporter.log.info("Display name for resource '{}/{}' changing from '{}' to '{}'", new Object[] { resourceTypeEntry.getName(), resourceDesc.getName(), resourceEntry.getDisplayName(), resourceDesc.getDisplayedName() });
                resourceEntry.setDisplayName(resourceDesc.getDisplayedName());
                isModified = true;
            }
            return !isModified;
        }
        
        private List<String> getActionNameList(final TResourceTypeDesc resourceTypeDesc) {
            final List<String> actionNameList = new ArrayList<String>();
            if (resourceTypeDesc.getActions() != null) {
                for (final TActionDesc actionDesc : resourceTypeDesc.getActions().getAction()) {
                    OesModelImporter.this.actionsNamesService.createOrUpdate(this.currentApplicationPolicy.getApplicationName(), resourceTypeDesc.getName(), actionDesc.getName(), actionDesc.getDisplayedName());
                    actionNameList.add(actionDesc.getName());
                }
            }
            return actionNameList;
        }
        
        private List<AttributeEntry<? extends DataType>> getAttributeList(final TResourceTypeDesc resourceTypeDesc) throws PolicyStoreException {
            final List<AttributeEntry<? extends DataType>> attributeEntryList = new ArrayList<AttributeEntry<? extends DataType>>();
            if (resourceTypeDesc.getAttributes() != null) {
                for (final TResourceTypeAttributeDesc resourceTypeAttributeDesc : resourceTypeDesc.getAttributes().getAttribute()) {
                    final String attributeName = resourceTypeAttributeDesc.getName();
                    final ExtensionManager extensionManager = this.currentApplicationPolicy.getExtensionManager();
                    attributeEntryList.add((AttributeEntry<? extends DataType>)extensionManager.getAttribute(attributeName));
                }
            }
            return attributeEntryList;
        }
        
        private void doImportAutorizationPolicy(final TAutorizationPolicyDesc autorizationPolicyDesc) throws PolicyStoreException, ParseException {
            final PolicyManager policyManager = this.currentApplicationPolicy.getPolicyManager();
            try {
                final PolicyEntry policyEntry = policyManager.getPolicy(autorizationPolicyDesc.getName());
                OesModelImporter.log.debug("Policy '{}' is found", (Object)autorizationPolicyDesc.getName());
                if (!this.isStoredPolicyEqualToImporting(policyEntry, autorizationPolicyDesc)) {
                    OesModelImporter.log.info("Policy '{}' is changed. Updating in store...", (Object)autorizationPolicyDesc.getName());
                    policyManager.modifyPolicy(policyEntry);
                }
            }
            catch (PolicyObjectNotFoundException e) {
                OesModelImporter.log.debug("Policy '{}' is not found. Creating...", (Object)autorizationPolicyDesc.getName());
                policyManager.createPolicy(autorizationPolicyDesc.getName(), autorizationPolicyDesc.getDisplayedName(), autorizationPolicyDesc.getDisplayedName(), (PolicyRuleEntry)new BasicPolicyRuleEntry(autorizationPolicyDesc.getName() + "Rule", "\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405 \u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405 \u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405 \"" + autorizationPolicyDesc.getDisplayedName() + "\"", "\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405 \u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405 \u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405\u043f\u0457\u0405 \"" + autorizationPolicyDesc.getDisplayedName() + "\"", PolicyRuleEntry.EffectType.GRANT, (RuleExpressionEntry)this.getRuleExpressionEntry(autorizationPolicyDesc.getCondition())), (List)this.getPrincipalEntryList(autorizationPolicyDesc), (CodeSourceEntry)null, (List)this.createResourceActionsEntryList(autorizationPolicyDesc.getResources().getResource()), (List)null, (List)this.createObligations(autorizationPolicyDesc.getObligations()), PolicyEntry.POLICY_SEMANTIC.OR);
            }
        }
        
        private List<ObligationEntry> createObligations(final TObligations obligations) throws ParseException, PolicyStoreException {
            if (obligations == null || obligations.getObligation() == null || obligations.getObligation().isEmpty()) {
                return null;
            }
            final List<ObligationEntry> resultList = new ArrayList<ObligationEntry>();
            OesModelImporter.log.debug("Policy contains obligations, processing");
            for (final TObligation obligation : obligations.getObligation()) {
                final List<AttributeAssignment<? extends DataType>> addingAttributes = new ArrayList<AttributeAssignment<? extends DataType>>();
                if (obligation.getConditionatribute() != null) {
                    for (final TObligationConditionAttribute conditionAttribute : obligation.getConditionatribute()) {
                        final String conditionValue = this.getRuleExpressionEntry(conditionAttribute).toString();
                        OesModelImporter.log.debug("Adding obligation condition attribute '{}': '{}'", (Object)conditionAttribute.getName(), (Object)conditionValue);
                        addingAttributes.add((AttributeAssignment<? extends DataType>)new AttributeAssignment(conditionAttribute.getName(), (DataType)new OpssString(conditionValue)));
                    }
                    for (final TObligationAttribute attribute : obligation.getAttribute()) {
                        DataType dataType;
                        if (TAttributeTypeEnum.BOOLEAN.equals(attribute.getType())) {
                            dataType = (DataType)new OpssBoolean((boolean)Boolean.valueOf(attribute.getValue()));
                        }
                        else if (TAttributeTypeEnum.INTEGER.equals(attribute.getType())) {
                            dataType = (DataType)new OpssInteger((int)Integer.valueOf(attribute.getValue()));
                        }
                        else if (TAttributeTypeEnum.STRING.equals(attribute.getType())) {
                            dataType = (DataType)new OpssString(attribute.getValue());
                        }
                        else if (TAttributeTypeEnum.DATE.equals(attribute.getType())) {
                            dataType = (DataType)new OpssDate(OesModelImporter.dateFormat.parse(attribute.getValue()));
                        }
                        else {
                            if (!TAttributeTypeEnum.DOUBLE.equals(attribute.getType())) {
                                throw new RuntimeException("Invalid obligation attribute type '" + attribute.getType() + "'");
                            }
                            dataType = (DataType)new OpssDouble(Double.parseDouble(attribute.getValue()));
                        }
                        OesModelImporter.log.debug("Adding obligation attribute '{}': '{}'", (Object)attribute.getName(), (Object)attribute.getValue());
                        addingAttributes.add((AttributeAssignment<? extends DataType>)new AttributeAssignment(attribute.getName(), dataType));
                    }
                }
                final ObligationEntry obligationEntry = (ObligationEntry)new BasicObligationEntry(obligation.getName(), obligation.getDisplayedName(), obligation.getDisplayedName(), (List)addingAttributes);
                resultList.add(obligationEntry);
            }
            return resultList;
        }
        
        private List<PrincipalEntry> getPrincipalEntryList(final TAutorizationPolicyDesc autorizationPolicyDesc) throws PolicyStoreException {
            final List<PrincipalEntry> principalEntryList = new ArrayList<PrincipalEntry>();
            final AppRoleManager appRoleManager = this.currentApplicationPolicy.getAppRoleManager();
            if (autorizationPolicyDesc.getRoles() != null && CollectionUtils.isNotEmpty((Collection)autorizationPolicyDesc.getRoles().getRolename())) {
                for (final TApplicationRoleName appRoleName : autorizationPolicyDesc.getRoles().getRolename()) {
                    principalEntryList.add((PrincipalEntry)appRoleManager.getAppRole(appRoleName.getName()));
                    OesModelImporter.log.debug("Application role '{}' added to authorization policy '{}'", (Object)appRoleName.getName(), (Object)autorizationPolicyDesc.getName());
                }
            }
            if (autorizationPolicyDesc.getGroups() != null && CollectionUtils.isNotEmpty((Collection)autorizationPolicyDesc.getGroups().getGroup())) {
                for (final TGroupPrincipalDesc groupPrincipalDesc : autorizationPolicyDesc.getGroups().getGroup()) {
                    if (OesModelImporter.this.ldapDao.isGroupExistByCn(groupPrincipalDesc.getCn())) {
                        OesModelImporter.log.debug("Group {cn: '{}''} for authorization policy '{}' is found in ldap", (Object)groupPrincipalDesc.getCn(), (Object)autorizationPolicyDesc.getName());
                        principalEntryList.add(this.createUserPrincipalEntry(groupPrincipalDesc.getCn()));
                    }
                    else if (OesModelImporter.this.policyAddNotExistentPrincipals) {
                        OesModelImporter.log.debug("Group {cn: '{}''} for authorization policy '{}' is NOT found in ldap,  but not skip this group", (Object)groupPrincipalDesc.getCn(), (Object)autorizationPolicyDesc.getName());
                        principalEntryList.add(this.createUserPrincipalEntry(groupPrincipalDesc.getCn()));
                    }
                    else {
                        OesModelImporter.log.error("Group {cn: '{}''} for authorization policy '{}' is NOT found in ldap, skip this group", (Object)groupPrincipalDesc.getCn(), (Object)autorizationPolicyDesc.getName());
                    }
                }
            }
            if (autorizationPolicyDesc.getUsers() != null && CollectionUtils.isNotEmpty((Collection)autorizationPolicyDesc.getUsers().getUser())) {
                for (final TUserPrincipalDesc userPrincipalDesc : autorizationPolicyDesc.getUsers().getUser()) {
                    String userCN = this.getUserCNFromLdap(userPrincipalDesc);
                    if (StringUtils.isNotEmpty(userCN)) {
                        OesModelImporter.log.debug("User {cn: '{}', sn: '{}'} for authorization policy '{}' is found in ldap", new Object[] { userPrincipalDesc.getCn(), userPrincipalDesc.getSn(), autorizationPolicyDesc.getName() });
                        principalEntryList.add(this.createUserPrincipalEntry(userCN));
                    }
                    else if (OesModelImporter.this.policyAddNotExistentPrincipals) {
                        OesModelImporter.log.error("User {cn: '{}', sn: '{}'} for authorization policy '{}' is NOT found in ldap,  but not skip this user", new Object[] { userPrincipalDesc.getCn(), userPrincipalDesc.getSn(), autorizationPolicyDesc.getName() });
                        userCN = userPrincipalDesc.getSn();
                        if (StringUtils.isEmpty(userCN)) {
                            userCN = userPrincipalDesc.getCn();
                        }
                        principalEntryList.add(this.createUserPrincipalEntry(userCN));
                    }
                    else {
                        OesModelImporter.log.error("User {cn: '{}', sn: '{}'} for authorization policy '{}' is NOT found in ldap,  skip this user", new Object[] { userPrincipalDesc.getCn(), userPrincipalDesc.getSn(), autorizationPolicyDesc.getName() });
                    }
                }
            }
            return principalEntryList;
        }
        
        private List<ResourceActionsEntry> createResourceActionsEntryList(final List<TResourceActionsRef> resourceActionsDescList) throws PolicyStoreException {
            final List<ResourceActionsEntry> resourceActionsEntryList = new ArrayList<ResourceActionsEntry>();
            for (final TResourceActionsRef resourceActionsDesc : resourceActionsDescList) {
                resourceActionsEntryList.add(this.createResourceActionsEntry(resourceActionsDesc));
            }
            return resourceActionsEntryList;
        }
        
        private ResourceActionsEntry createResourceActionsEntry(final TResourceActionsRef resourceActionsDesc) throws PolicyStoreException {
            final ResourceManager resourceManager = this.currentApplicationPolicy.getResourceManager();
            final ResourceSearchQuery rQuery = new ResourceSearchQuery((List)Arrays.asList(new ResourceSearchQuery(ResourceSearchQuery.SEARCH_PROPERTY.RESOURCE_TYPE, false, ComparatorType.EQUALITY, (Object)resourceActionsDesc.getResourceTypeName(), SearchQuery.MATCHER.EXACT), new ResourceSearchQuery(ResourceSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, (Object)resourceActionsDesc.getResourceName(), SearchQuery.MATCHER.EXACT)), false, false);
            final List<ResourceEntry> rList = (List<ResourceEntry>)resourceManager.getResources(rQuery);
            if (rList.size() != 1) {
                throw new RuntimeException(rList.size() + " resources with name " + resourceActionsDesc.getResourceName() + " found");
            }
            final List<String> actionList = new ArrayList<String>();
            if (resourceActionsDesc.getActions().getAction() != null) {
                for (final TActionRef actionRef : resourceActionsDesc.getActions().getAction()) {
                    actionList.add(actionRef.getName());
                }
            }
            return (ResourceActionsEntry)new BasicResourceActionsEntry((ResourceEntry)rList.get(0), (List)actionList);
        }
        
        private boolean isStoredPolicyEqualToImporting(final PolicyEntry policyEntry, final TAutorizationPolicyDesc autorizationPolicyDesc) throws PolicyStoreException, ParseException {
            boolean isModified = false;
            if (!policyEntry.getDisplayName().equals(autorizationPolicyDesc.getDisplayedName())) {
                OesModelImporter.log.info("Display name for policy '{}' changing from '{}' to '{}'", new Object[] { autorizationPolicyDesc.getName(), policyEntry.getDisplayName(), autorizationPolicyDesc.getDisplayedName() });
                policyEntry.setDisplayName(autorizationPolicyDesc.getDisplayedName());
                isModified = true;
            }
            final List<PrincipalEntry> storedPrincipalList = (List<PrincipalEntry>)policyEntry.getPrincipals();
            final List<PrincipalEntry> importingPrincipalList = this.getPrincipalEntryList(autorizationPolicyDesc);
            final Collection<PrincipalEntry> newPrincipalList = (Collection<PrincipalEntry>)CollectionUtils.select((Collection)importingPrincipalList, (Predicate)new Predicate() {
                public boolean evaluate(final Object importingPrincipalEntryObject) {
                    return null == CollectionUtils.find((Collection)storedPrincipalList, (Predicate)new Predicate() {
                        public boolean evaluate(final Object storedPrincipalEntryObject) {
                            final PrincipalEntry storedPrincipalEntry = (PrincipalEntry)storedPrincipalEntryObject;
                            final PrincipalEntry importingPrincipalEntry = (PrincipalEntry)importingPrincipalEntryObject;
                            return importingPrincipalEntry.getName().equals(storedPrincipalEntry.getName()) && importingPrincipalEntry.getClassName().equals(storedPrincipalEntry.getClassName());
                        }
                    });
                }
            });
            if (newPrincipalList.size() > 0) {
                final Collection newPrincipalNames = CollectionUtils.transformedCollection((Collection)newPrincipalList, (Transformer)new Transformer() {
                    public Object transform(final Object input) {
                        return ((PrincipalEntry)input).getName();
                    }
                });
                OesModelImporter.log.info("Principal list for policy '{}' changed. Principals '{}' added", (Object)autorizationPolicyDesc.getName(), (Object)Arrays.toString(newPrincipalNames.toArray()));
                for (final PrincipalEntry principalEntry : newPrincipalList) {
                    policyEntry.addPrincipal(principalEntry);
                }
                isModified = true;
            }
            else {
                OesModelImporter.log.debug("Principal list for policy '{}' is not changed", (Object)autorizationPolicyDesc.getName());
            }
            final List<ResourceActionsEntry> storedResourceActionsList = (List<ResourceActionsEntry>)policyEntry.getResourceActions();
            final List<TResourceActionsRef> importingResourceActionsList = autorizationPolicyDesc.getResources().getResource();
            for (final TResourceActionsRef importingResourceActionsDesc : importingResourceActionsList) {
                final ResourceActionsEntry resourceActionsEntry = (ResourceActionsEntry)CollectionUtils.find((Collection)storedResourceActionsList, (Predicate)new Predicate() {
                    public boolean evaluate(final Object input) {
                        final ResourceActionsEntry storedResourceActionsEntry = (ResourceActionsEntry)input;
                        return storedResourceActionsEntry.getResourceType().equals(importingResourceActionsDesc.getResourceTypeName()) && storedResourceActionsEntry.getResourceEntry().getName().equals(importingResourceActionsDesc.getResourceName());
                    }
                });
                if (resourceActionsEntry == null) {
                    final ResourceActionsEntry addingResourceActionsEntry = this.createResourceActionsEntry(importingResourceActionsDesc);
                    OesModelImporter.log.info("New resource actions entry adding {resource: '{}/{}', actions: {}}", new Object[] { addingResourceActionsEntry.getResourceType(), addingResourceActionsEntry.getResourceEntry().getName(), Arrays.toString(addingResourceActionsEntry.getActions().toArray()) });
                    storedResourceActionsList.add(addingResourceActionsEntry);
                    isModified = true;
                }
                else {
                    OesModelImporter.log.debug("Resource actions entry is found {resource: '{}/{}', actions: {}}", new Object[] { resourceActionsEntry.getResourceType(), resourceActionsEntry.getResourceEntry().getName(), Arrays.toString(resourceActionsEntry.getActions().toArray()) });
                    final Set<String> storedActionNames = (Set<String>)resourceActionsEntry.getActions();
                    final List<String> importingActionNameList = new ArrayList<String>();
                    for (final TActionRef actionRef : importingResourceActionsDesc.getActions().getAction()) {
                        importingActionNameList.add(actionRef.getName());
                    }
                    final Collection newActionNames = CollectionUtils.subtract((Collection)importingActionNameList, (Collection)storedActionNames);
                    if (newActionNames.size() <= 0) {
                        continue;
                    }
                    OesModelImporter.log.info("New actions '{}' adding to resource '{}/{}' for policy '{}'", new Object[] { Arrays.toString(newActionNames.toArray()), importingResourceActionsDesc.getResourceTypeName(), importingResourceActionsDesc.getResourceName(), autorizationPolicyDesc.getName() });
                    resourceActionsEntry.setActions((List)new ArrayList(CollectionUtils.union((Collection)storedActionNames, newActionNames)));
                }
            }
            final List<PolicyRuleEntry> policyRuleEntryList = (List<PolicyRuleEntry>)policyEntry.getRules();
            if (CollectionUtils.size((Object)policyRuleEntryList) != 1) {
                throw new RuntimeException("Invalid count '" + CollectionUtils.size((Object)policyRuleEntryList) + "' of policy rule entry in policy '" + policyEntry.getName() + "'");
            }
            final PolicyRuleEntry policyRuleEntry = policyRuleEntryList.get(0);
            final RuleExpressionEntry storedCondition = policyRuleEntry.getCondition();
            final RuleExpressionEntry importingCondition = this.getRuleExpressionEntry(autorizationPolicyDesc.getCondition());
            if (storedCondition == null) {
                if (importingCondition == null) {
                    OesModelImporter.log.debug("Stored and importing conditions are null");
                }
                else {
                    OesModelImporter.log.info("Condition changing from null to '{}' for policy '{}'", (Object)importingCondition.toString(), (Object)policyEntry.getName());
                    policyRuleEntry.setCondition(importingCondition);
                    ObligationUtils.replaceObligation(policyEntry, importingCondition, "ADDITIONAL_CONDITION", "CONDITION_IN_XML");
                    isModified = true;
                }
            }
            else if (importingCondition == null) {
                OesModelImporter.log.info("Condition changing from '{}' to null", (Object)storedCondition.toString());
                policyRuleEntry.setCondition((RuleExpressionEntry)null);
                ObligationUtils.deleteObligation(policyEntry, "ADDITIONAL_CONDITION");
                isModified = true;
            }
            else {
                final String importingConditionStr = importingCondition.toString();
                final String storedConditionStr = storedCondition.toString();
                if (!importingConditionStr.equals(storedConditionStr)) {
                    OesModelImporter.log.info("Conditiong changing from '{}' to '{}' for policy '{}'", new Object[] { storedConditionStr, importingConditionStr, policyEntry.getName() });
                    policyRuleEntry.setCondition(importingCondition);
                    ObligationUtils.replaceObligation(policyEntry, importingCondition, "ADDITIONAL_CONDITION", "CONDITION_IN_XML");
                    isModified = true;
                }
                else {
                    OesModelImporter.log.info("Conditiong is not change value '{}'", (Object)storedConditionStr);
                }
            }
            return !isModified;
        }
        
        private RuleExpressionEntry<OpssBoolean> getRuleExpressionEntry(final TCondition conditionDesc) throws PolicyStoreException, ParseException {
            if (conditionDesc == null) {
                return null;
            }
            final BuildOPSSConditionHandler conditionHandler = new BuildOPSSConditionHandler(this.currentApplicationPolicy.getExtensionManager());
            final ConditionParser conditionParser = new ConditionParser(conditionDesc, conditionHandler);
            conditionParser.process();
            return conditionHandler.getResult();
        }
        
        private PrincipalEntry createUserPrincipalEntry(final String cn) {
            return (PrincipalEntry)new BasicPrincipalEntry("weblogic.security.principal.WLSUserImpl", cn);
        }
        
        private PrincipalEntry createGroupPrincipalEntry(final String cn) {
            return (PrincipalEntry)new BasicPrincipalEntry("weblogic.security.principal.WLSGroupImpl", cn);
        }
        
        private String getUserCNFromLdap(final TUserPrincipalDesc userPrincipalDesc) {
            String userCN = null;
            if (StringUtils.isNotEmpty(userPrincipalDesc.getCn()) && OesModelImporter.this.ldapDao.isUserExistByCn(userPrincipalDesc.getCn())) {
                userCN = userPrincipalDesc.getCn();
            }
            if (StringUtils.isEmpty(userCN) && StringUtils.isNotEmpty(userPrincipalDesc.getSn())) {
                userCN = OesModelImporter.this.ldapDao.getCnBySN(userPrincipalDesc.getSn());
            }
            return userCN;
        }
        
        private class PolicyAutoGenerator
        {
            private void doGeneratePolicies() throws PolicyStoreException {
                final ResourceTypeManager resourceTypeManager = Importer.this.currentApplicationPolicy.getResourceTypeManager();
                final ResourceTypeSearchQuery resourceTypeSearchQuery = new ResourceTypeSearchQuery(ResourceTypeSearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, "-", SearchQuery.MATCHER.ANY);
                final List<ResourceTypeEntry> resourceTypeEntryList = (List<ResourceTypeEntry>)resourceTypeManager.getResourceTypes(resourceTypeSearchQuery);
                if (CollectionUtils.isNotEmpty((Collection)resourceTypeEntryList)) {
                    for (final ResourceTypeEntry resourceTypeEntry : resourceTypeEntryList) {
                        OesModelImporter.log.info("Deleting resource type '{}'", (Object)resourceTypeEntry.getName());
                        resourceTypeManager.deleteResourceType(resourceTypeEntry.getName(), true);
                    }
                }
                else {
                    OesModelImporter.log.debug("No resource type is found for delete");
                }
                final PolicyManager policyManager = Importer.this.currentApplicationPolicy.getPolicyManager();
                final PolicySearchQuery policySearchQuery = new PolicySearchQuery(PolicySearchQuery.SEARCH_PROPERTY.NAME, false, ComparatorType.EQUALITY, (Object)"-", SearchQuery.MATCHER.ANY);
                final List<PolicyEntry> policyEntryList = (List<PolicyEntry>)policyManager.getPolicies(policySearchQuery);
                if (CollectionUtils.isNotEmpty((Collection)policyEntryList)) {
                    for (final PolicyEntry policyEntry : policyEntryList) {
                        OesModelImporter.log.info("Deleting policy '{}'", (Object)policyEntry.getName());
                    }
                }
                else {
                    OesModelImporter.log.debug("No policy is found for delete");
                }
            }
        }
    }
    
    private class ConditionParser
    {
        private TCondition conditionDesc;
        private ConditionHandler conditionHandler;
        
        public ConditionParser(final TCondition conditionDesc, final ConditionHandler conditionHandler) {
            this.conditionDesc = conditionDesc;
            this.conditionHandler = conditionHandler;
        }
        
        public void process() throws PolicyStoreException, ParseException {
            this.processFunction(this.conditionDesc.getF());
        }
        
        private void processFunction(final TFunction function) throws PolicyStoreException, ParseException {
            this.conditionHandler.startF(function);
            for (final Object object : function.getParOrListOrF()) {
                if (object instanceof TParameter) {
                    this.conditionHandler.addPar((TParameter)object);
                }
                else if (object instanceof TList) {
                    this.conditionHandler.startList();
                    for (final TParameter parameter : ((TList)object).getPar()) {
                        this.conditionHandler.addPar(parameter);
                    }
                    this.conditionHandler.finishList();
                }
                else {
                    if (!(object instanceof TFunction)) {
                        throw new RuntimeException("Unknown object");
                    }
                    this.processFunction((TFunction)object);
                }
            }
            this.conditionHandler.finishF();
        }
    }
    
    private class BuildOPSSConditionHandler implements ConditionHandler
    {
        private ExtensionManager xMgr;
        private Stack<Expression> expressionStack;
        private List currentList;
        private RuleExpressionEntry<OpssBoolean> result;
        
        public BuildOPSSConditionHandler(final ExtensionManager xMgr) {
            this.expressionStack = new Stack<Expression>();
            this.xMgr = xMgr;
        }
        
        @Override
        public void startF(final TFunction function) throws PolicyStoreException {
            final FunctionEntry functionEntry = this.xMgr.getFunction(function.getNm().name());
            final Expression expression = new Expression(functionEntry);
            if (!this.expressionStack.empty()) {
                this.expressionStack.peek().addExpressionComponent((ExpressionComponent)expression);
            }
            this.expressionStack.add(expression);
        }
        
        @Override
        public void finishF() throws PolicyStoreException {
            final Expression expression = this.expressionStack.pop();
            if (this.expressionStack.isEmpty()) {
                this.result = (RuleExpressionEntry<OpssBoolean>)new BooleanExpressionEntry((ExpressionComponent)expression);
            }
        }
        
        @Override
        public void addPar(final TParameter parameter) throws PolicyStoreException, ParseException {
            if (StringUtils.isEmpty(parameter.getNm())) {
                DataType dataType;
                if (TValueType.STR.equals(parameter.getTp())) {
                    dataType = (DataType)new OpssString(parameter.getVal());
                }
                else if (TValueType.BOOL.equals(parameter.getTp())) {
                    dataType = (DataType)new OpssBoolean((boolean)Boolean.valueOf(parameter.getVal()));
                }
                else if (TValueType.INT.equals(parameter.getTp())) {
                    dataType = (DataType)new OpssInteger((int)Integer.valueOf(parameter.getVal()));
                }
                else if (TValueType.DBL.equals(parameter.getTp())) {
                    dataType = (DataType)new OpssDouble(Double.parseDouble(parameter.getVal()));
                }
                else {
                    if (!TValueType.DATE.equals(parameter.getTp())) {
                        throw new RuntimeException("Invalid attribute type '" + parameter.getTp() + "'");
                    }
                    dataType = (DataType)new OpssDate(OesModelImporter.dateFormat.parse(parameter.getVal()));
                }
                if (this.currentList != null) {
                    this.currentList.add(dataType);
                }
                else {
                    this.expressionStack.peek().addExpressionComponent((ExpressionComponent)dataType);
                }
            }
            else {
                this.expressionStack.peek().addExpressionComponent((ExpressionComponent)this.xMgr.getAttribute(parameter.getNm()));
            }
        }
        
        @Override
        public void startList() throws PolicyStoreException {
            this.currentList = new ArrayList();
        }
        
        @Override
        public void finishList() throws PolicyStoreException {
            final ValueCollection valueCollection = new ValueCollection();
            valueCollection.setValues(this.currentList);
            this.expressionStack.peek().addExpressionComponent((ExpressionComponent)valueCollection);
            this.currentList = null;
        }
        
        public RuleExpressionEntry<OpssBoolean> getResult() {
            return this.result;
        }
    }
    
    private interface ConditionHandler
    {
        void startF(final TFunction p0) throws PolicyStoreException;
        
        void finishF() throws PolicyStoreException;
        
        void addPar(final TParameter p0) throws PolicyStoreException, ParseException;
        
        void startList() throws PolicyStoreException;
        
        void finishList() throws PolicyStoreException;
    }
}
