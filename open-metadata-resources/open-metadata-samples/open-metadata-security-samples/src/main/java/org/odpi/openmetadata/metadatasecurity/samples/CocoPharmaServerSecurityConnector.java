/* SPDX-License-Identifier: Apache 2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.metadatasecurity.samples;


import org.odpi.openmetadata.frameworks.connectors.ffdc.InvalidParameterException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException;
import org.odpi.openmetadata.frameworks.connectors.ffdc.UserNotAuthorizedException;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Asset;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.Connection;
import org.odpi.openmetadata.metadatasecurity.connectors.OpenMetadataServerSecurityConnector;
import org.odpi.openmetadata.metadatasecurity.properties.AssetAuditHeader;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.*;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.typedefs.TypeDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * CocoPharmaServerSecurityConnector provides a specific security connector for Coco Pharmaceuticals
 * users that overrides the default behavior of that open metadata security connector that does
 * not allow any access to anything.
 */
public class CocoPharmaServerSecurityConnector extends OpenMetadataServerSecurityConnector
{
    /*
     * These variables represent the different groups of user.  Typically these would be
     * implemented as a look up to a user directory such as LDAP rather than in memory lists.
     * The lists are used here to make the demo easier to set up.
     */
    private List<String>              allUsers            = new ArrayList<>();

    private List<String>              assetOnboardingExit = new ArrayList<>();
    private List<String>              serverAdmins        = new ArrayList<>();
    private List<String>              serverOperators     = new ArrayList<>();
    private List<String>              serverInvestigators = new ArrayList<>();
    private List<String>              metadataArchitects  = new ArrayList<>();
    private List<String>              npaAccounts         = new ArrayList<>();

    private List<String>              defaultZoneMembership = new ArrayList<>();

    private Map<String, List<String>> zoneAccess   = new HashMap<>();
    private Map<String, String>       ownerZones   = new HashMap<>();

    /*
     * Zones requiring special processing
     */
    private final String personalFilesZoneName   = "personal-files";
    private final String quarantineZoneName      = "quarantine";
    private final String dataLakeZoneName        = "data-lake";


    /**
     * Constructor sets up the security groups
     */
    public CocoPharmaServerSecurityConnector()
    {
        List<String>              allEmployees        = new ArrayList<>();
        List<String>              assetOnboarding     = new ArrayList<>();

        /*
         * Standard zones in use by Coco Pharmaceuticals
         */
        final String researchZoneName        = "research";
        final String hrZoneName              = "human-resources";
        final String financeZoneName         = "finance";
        final String clinicalTrialsZoneName  = "clinical-trials";
        final String infrastructureZoneName  = "infrastructure";
        final String developmentZoneName     = "development";
        final String manufacturingZoneName   = "manufacturing";
        final String governanceZoneName      = "governance";
        final String trashCanZoneName        = "trash-can";

        final String zachNowUserId        = "zach";
        final String steveStarterUserId   = "steves";
        final String terriDaringUserId    = "terri";
        final String tanyaTidieUserId     = "tanyatidie";
        final String pollyTaskerUserId    = "pollytasker";
        final String tessaTubeUserId      = "tessatube";
        final String callieQuartileUserId = "calliequartile";
        final String ivorPadlockUserId    = "ivorpadlock";
        final String bobNitterUserId      = "bobnitter";
        final String faithBrokerUserId    = "faithbroker";
        final String sallyCounterUserId   = "sallycounter";
        final String lemmieStageUserId    = "lemmiestage";
        final String erinOverviewUserId   = "erinoverview";
        final String harryHopefulUserId   = "harryhopeful";
        final String garyGeekeUserId      = "garygeeke";
        final String grantAbleUserId      = "grantable";
        final String robbieRecordsUserId  = "robbierecords";
        final String reggieMintUserId     = "reggiemint";
        final String peterProfileUserId   = "peterprofile";
        final String nancyNoahUserId      = "nancynoah";
        final String sidneySeekerUserId   = "sidneyseeker";
        final String tomTallyUserId       = "tomtally";
        final String julieStitchedUserId  = "juliestitched";
        final String desSignaUserId       = "designa";
        final String angelaCummingUserId  = "angelacummings";
        final String julesKeeperUserId    = "jukeskeeper";
        final String stewFasterUserId     = "stewFaster";

        final String archiverUserId    = "archiver01";
        final String etlEngineUserId   = "dlETL";

        /*
         * Set up default zone membership
         */
        defaultZoneMembership.add(quarantineZoneName);

        /*
         * Add all the users into the all group
         */
        allUsers.add(zachNowUserId);
        allUsers.add(steveStarterUserId);
        allUsers.add(terriDaringUserId);
        allUsers.add(tanyaTidieUserId);
        allUsers.add(pollyTaskerUserId);
        allUsers.add(tessaTubeUserId);
        allUsers.add(callieQuartileUserId);
        allUsers.add(ivorPadlockUserId);
        allUsers.add(bobNitterUserId);
        allUsers.add(faithBrokerUserId);
        allUsers.add(sallyCounterUserId);
        allUsers.add(lemmieStageUserId);
        allUsers.add(erinOverviewUserId);
        allUsers.add(harryHopefulUserId);
        allUsers.add(garyGeekeUserId);
        allUsers.add(grantAbleUserId);
        allUsers.add(robbieRecordsUserId);
        allUsers.add(reggieMintUserId);
        allUsers.add(peterProfileUserId);
        allUsers.add(nancyNoahUserId);
        allUsers.add(sidneySeekerUserId);
        allUsers.add(tomTallyUserId);
        allUsers.add(julieStitchedUserId);
        allUsers.add(desSignaUserId);
        allUsers.add(angelaCummingUserId);
        allUsers.add(julesKeeperUserId);
        allUsers.add(stewFasterUserId);
        allUsers.add(archiverUserId);
        allUsers.add(etlEngineUserId);

        allEmployees.add(zachNowUserId);
        allEmployees.add(steveStarterUserId);
        allEmployees.add(terriDaringUserId);
        allEmployees.add(tanyaTidieUserId);
        allEmployees.add(pollyTaskerUserId);
        allEmployees.add(tessaTubeUserId);
        allEmployees.add(callieQuartileUserId);
        allEmployees.add(ivorPadlockUserId);
        allEmployees.add(bobNitterUserId);
        allEmployees.add(faithBrokerUserId);
        allEmployees.add(sallyCounterUserId);
        allEmployees.add(lemmieStageUserId);
        allEmployees.add(erinOverviewUserId);
        allEmployees.add(harryHopefulUserId);
        allEmployees.add(garyGeekeUserId);
        allEmployees.add(reggieMintUserId);
        allEmployees.add(peterProfileUserId);
        allEmployees.add(sidneySeekerUserId);
        allEmployees.add(tomTallyUserId);
        allEmployees.add(julesKeeperUserId);
        allEmployees.add(stewFasterUserId);

        assetOnboarding.add(peterProfileUserId);
        assetOnboarding.add(erinOverviewUserId);
        assetOnboardingExit.add(erinOverviewUserId);

        serverAdmins.add(garyGeekeUserId);
        serverOperators.add(garyGeekeUserId);
        serverInvestigators.add(garyGeekeUserId);

        metadataArchitects.add(erinOverviewUserId);
        metadataArchitects.add(peterProfileUserId);

        npaAccounts.add(archiverUserId);
        npaAccounts.add(etlEngineUserId);

        List<String> zoneSetUp = new ArrayList<>();

        /*
         * Set up the assignment of zones for specific users
         */
        ownerZones.put(tanyaTidieUserId, clinicalTrialsZoneName);

        /*
         * Set up the zones
         */
        zoneAccess.put(trashCanZoneName, npaAccounts);
        zoneAccess.put(personalFilesZoneName, allEmployees);
        zoneAccess.put(quarantineZoneName, assetOnboarding);
        zoneAccess.put(dataLakeZoneName, allEmployees);

        zoneSetUp.add(callieQuartileUserId);
        zoneSetUp.add(tessaTubeUserId);

        zoneAccess.put(researchZoneName, zoneSetUp);

        zoneSetUp.add(tanyaTidieUserId);

        zoneAccess.put(clinicalTrialsZoneName, zoneSetUp);

        zoneSetUp = new ArrayList<>();
        zoneSetUp.add(faithBrokerUserId);

        zoneAccess.put(hrZoneName, zoneSetUp);

        zoneSetUp = new ArrayList<>();
        zoneSetUp.add(reggieMintUserId);
        zoneSetUp.add(tomTallyUserId);
        zoneSetUp.add(sallyCounterUserId);

        zoneAccess.put(financeZoneName, zoneSetUp);

        zoneSetUp = new ArrayList<>();
        zoneSetUp.add(garyGeekeUserId);
        zoneSetUp.add(erinOverviewUserId);
        zoneSetUp.add(peterProfileUserId);

        zoneAccess.put(infrastructureZoneName, zoneSetUp);

        zoneSetUp = new ArrayList<>();
        zoneSetUp.add(pollyTaskerUserId);
        zoneSetUp.add(bobNitterUserId);
        zoneSetUp.add(lemmieStageUserId);
        zoneSetUp.add(nancyNoahUserId);
        zoneSetUp.add(desSignaUserId);

        zoneAccess.put(developmentZoneName, zoneSetUp);

        zoneSetUp = new ArrayList<>();
        zoneSetUp.add(stewFasterUserId);

        zoneAccess.put(manufacturingZoneName, zoneSetUp);

        zoneSetUp = new ArrayList<>();
        zoneSetUp.add(garyGeekeUserId);
        zoneSetUp.add(erinOverviewUserId);
        zoneSetUp.add(julesKeeperUserId);
        zoneSetUp.add(pollyTaskerUserId);
        zoneSetUp.add(faithBrokerUserId);
        zoneSetUp.add(reggieMintUserId);

        zoneAccess.put(governanceZoneName, zoneSetUp);
    }


    /**
     * Check that the calling user is authorized to issue a (any) request to the OMAG Server Platform.
     *
     * @param userId calling user
     *
     * @throws UserNotAuthorizedException the user is not authorized to access this function
     */
    @Override
    public void  validateUserForServer(String   userId) throws UserNotAuthorizedException
    {
        if (allUsers.contains(userId))
        {
            return;
        }

        super.validateUserForServer(userId);
    }


    /**
     * Check that the calling user is authorized to update the configuration for a server.
     *
     * @param userId calling user
     *
     * @throws UserNotAuthorizedException the user is not authorized to change configuration
     */
    @Override
    public void  validateUserAsServerAdmin(String   userId) throws UserNotAuthorizedException
    {
        if (serverAdmins.contains(userId))
        {
            return;
        }

        super.validateUserAsServerAdmin(userId);
    }


    /**
     * Check that the calling user is authorized to issue operator requests to the OMAG Server.
     *
     * @param userId calling user
     *
     * @throws UserNotAuthorizedException the user is not authorized to issue operator commands to this server
     */
    @Override
    public void  validateUserAsServerOperator(String   userId) throws UserNotAuthorizedException
    {
        if (serverOperators.contains(userId))
        {
            return;
        }

        super.validateUserAsServerOperator(userId);
    }


    /**
     * Check that the calling user is authorized to issue operator requests to the OMAG Server.
     *
     * @param userId calling user
     *
     * @throws UserNotAuthorizedException the user is not authorized to issue diagnostic commands to this server
     */
    @Override
    public void  validateUserAsServerInvestigator(String   userId) throws UserNotAuthorizedException
    {
        if (serverInvestigators.contains(userId))
        {
            return;
        }

        super.validateUserAsServerInvestigator(userId);
    }


    /**
     * Check that the calling user is authorized to issue this request.
     *
     * @param userId calling user
     * @param serviceName name of called service
     *
     * @throws UserNotAuthorizedException the user is not authorized to access this service
     */
    @Override
    public void  validateUserForService(String   userId,
                                        String   serviceName) throws UserNotAuthorizedException
    {
        if (allUsers.contains(userId))
        {
            return;
        }

        super.validateUserForService(userId, serviceName);
    }


    /**
     * Check that the calling user is authorized to issue this specific request.
     *
     * @param userId calling user
     * @param serviceName name of called service
     * @param serviceOperationName name of called operation
     *
     * @throws UserNotAuthorizedException the user is not authorized to access this service
     */
    @Override
    public void  validateUserForServiceOperation(String   userId,
                                                 String   serviceName,
                                                 String   serviceOperationName) throws UserNotAuthorizedException
    {
        final String  assetOwnerServiceName = "Asset Owner OMAS";
        final String  deleteAssetOperationName = "deleteAsset";

        /*
         * Coco Pharmaceuticals have decided that the assetDelete method from Asset Owner OMAS is too powerful
         * to use and so this test means that only non-personal accounts (NPA) can use it.
         *
         * They delete an asset by moving it to the "trash-can" zone where it is cleaned up by automated
         * processes the next day.
         */
        if ((assetOwnerServiceName.equals(serviceName)) && (deleteAssetOperationName.equals(serviceOperationName)))
        {
            if (npaAccounts.contains(userId))
            {
                return;
            }
        }
        else
        {
            if (allUsers.contains(userId))
            {
                return;
            }
        }

        super.validateUserForServiceOperation(userId, serviceName, serviceOperationName);
    }


    /**
     * Tests for whether a specific user should have access to a connection.
     *
     * @param userId identifier of user
     * @param connection connection object
     * @throws UserNotAuthorizedException the user is not authorized to access this service
     */
    @Override
    public void  validateUserForConnection(String     userId,
                                           Connection connection) throws UserNotAuthorizedException
    {
        if (connection == null)
        {
            return;
        }

        if ((connection.getClearPassword() == null) &&
            (connection.getEncryptedPassword() == null) &&
            (connection.getSecuredProperties() == null))
        {
            return;
        }

        if (npaAccounts.contains(userId))
        {
            return;
        }

        super.validateUserForConnection(userId, connection);
    }


    /**
     * Tests for whether a specific user should have access to an assets based on its zones.
     *
     * @param userId identifier of user
     * @param assetZones name of the zones
     * @param updateRequested is the asset to be changed
     * @param zoneMembershipChanged is the asset's zone membership to be changed
     * @param userIsAssetOwner is the user one of the owner's of the asset
     * @return whether the user is authorized to access asset in these zones
     */
    private boolean userHasAccessToAssetZones(String           userId,
                                              List<String>     assetZones,
                                              boolean          updateRequested,
                                              boolean          zoneMembershipChanged,
                                              boolean          userIsAssetOwner)
    {
        List<String> testZones;

        /*
         * The quarantine zone is the default zone if it is not set up
         */
        if ((assetZones == null) || (assetZones.isEmpty()))
        {
            testZones = new ArrayList<>();
            testZones.add(quarantineZoneName);
        }
        else
        {
            testZones = assetZones;
        }

        for (String zoneName : testZones)
        {
            if (zoneName != null)
            {
                /*
                 * The data lake zone is special - only npaAccounts can update assets in the data lake zone.
                 * Another user may update these assets only if they have access via another one of the asset's zone.
                 */
                if ((zoneName.equals(dataLakeZoneName)) && (updateRequested))
                {
                    if (npaAccounts.contains(userId))
                    {
                        return true;
                    }
                }

                /*
                 * Access to personal files is only permitted by the owner of the asset.  If they assign the
                 * asset to another zone, this may allow others to read and change the asset.
                 */
                else if (zoneName.equals(personalFilesZoneName))
                {
                    if (userIsAssetOwner)
                    {
                        return true;
                    }
                }

                /*
                 * Standard look up of user's assigned to zones
                 */
                else
                {
                    List<String> zoneAccounts = zoneAccess.get(zoneName);

                    if ((zoneAccounts != null) && (zoneAccounts.contains(userId)))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /**
     * Adds the requested zone to the list of zones for an asset (avoiding duplicates).
     *
     * @param currentZones current list of zones
     * @param zoneToAdd new zone
     * @return new list of zones
     */
    private List<String>  addZoneName(List<String>   currentZones,
                                      String         zoneToAdd)
    {
        if (zoneToAdd == null)
        {
            return currentZones;
        }

        List<String>  resultingZones;

        if (currentZones == null)
        {
            resultingZones = new ArrayList<>();
            resultingZones.add(zoneToAdd);
        }
        else
        {
            resultingZones = currentZones;

            if (! resultingZones.contains(zoneToAdd))
            {
                resultingZones.add(zoneToAdd);
            }
        }

        return resultingZones;
    }


    /**
     * Remove the requested zone from the list of zones if it is present.
     *
     * @param currentZones current list of zones
     * @param zoneToRemove obsolete zone
     * @return new list of zones
     */
    private List<String>  removeZoneName(List<String>   currentZones,
                                         String         zoneToRemove)
    {
        if ((zoneToRemove == null) || (currentZones == null))
        {
            return currentZones;
        }

        List<String>  resultingZones = new ArrayList<>(currentZones);
        resultingZones.remove(zoneToRemove);

        return resultingZones;
    }


    /**
     * Tests for whether a specific user is the owner of an asset.  This test does not cover
     * the use of profile names for asset owner.
     *
     * @param userId identifier of user
     * @param asset asset to test
     * @return  boolean flag
     */
    private boolean  isUserAssetOwner(String     userId,
                                      Asset      asset)
    {
        if (asset != null)
        {
            if (userId != null)
            {
                return userId.equals(asset.getOwner());
            }
        }

        return false;
    }


    /**
     * Test whether the zone has changed because this may need special processing.
     *
     * @param oldZoneMembership zone membership before update
     * @param newZoneMembership zone membership after update
     * @return boolean true if they are different
     */
    private boolean  hasZoneChanged(List<String> oldZoneMembership,
                                    List<String> newZoneMembership)
    {
        /*
         * Are they the same object or both null?
         */
        if (oldZoneMembership == newZoneMembership)
        {
            return false;
        }

        /*
         * If either is null they are different because already tested that they are not both null.
         */
        if ((oldZoneMembership == null) || (newZoneMembership == null))
        {
            return true;
        }

        /*
         * If they each contain the other then they are equal
         */
        return ! ((oldZoneMembership.containsAll(newZoneMembership)) &&
                  (newZoneMembership.containsAll(oldZoneMembership)));
    }


    /**
     * Test to see if a specific zone has been removed.
     *
     * @param zoneName name of zone ot test for
     * @param oldZoneMembership old zone membership
     * @param newZoneMembership new zone membership
     * @return flag - true if the zone has been removed; otherwise false
     */
    private boolean  zoneBeenRemoved(String       zoneName,
                                     List<String> oldZoneMembership,
                                     List<String> newZoneMembership)
    {
        /*
         * Being defensive to prevent NPEs in the server processing.
         */
        if (zoneName == null)
        {
            return false;
        }

        /*
         * Are they the same object or both null?
         */
        if (oldZoneMembership == newZoneMembership)
        {
            return false;
        }

        /*
         * If the old zone is null then nothing could have been removed.
         */
        if (oldZoneMembership == null)
        {
            return false;
        }

        if (oldZoneMembership.contains(zoneName))
        {
            /*
             * Determine if the new zone still contains the zone of interest.
             */
            if (newZoneMembership == null)
            {
                return true;
            }
            else if (newZoneMembership.contains(zoneName))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }



    /**
     * Determine the appropriate setting for the asset zones depending on the content of the asset and the
     * default zones.  This is called whenever a new asset is created.
     *
     * The default behavior is to use the default values, unless the zones have been explicitly set up,
     * in which case, they are left unchanged.
     *
     * @param defaultZones setting of the default zones for the service
     * @param asset initial values for the asset
     *
     * @return list of zones to set in the asset
     * @throws InvalidParameterException one of the asset values is invalid
     * @throws PropertyServerException there is a problem calculating the zones
     */
    @Override
    public List<String> initializeAssetZones(List<String>  defaultZones,
                                             Asset         asset) throws InvalidParameterException,
                                                                         PropertyServerException
    {
        if (asset != null)
        {
            if (asset.getZoneMembership() == null)
            {
                return defaultZoneMembership;
            }
        }
        return super.initializeAssetZones(defaultZones, asset);
    }


    /**
     * Determine the appropriate setting for the asset zones depending on the content of the asset and the
     * settings of both default zones and supported zones.  This method is called whenever an asset's
     * values are changed.
     *
     * The default behavior is to keep the updated zones as they are.
     *
     * @param defaultZones setting of the default zones for the service
     * @param supportedZones setting of the supported zones for the service
     * @param originalAsset original values for the asset
     * @param updatedAsset updated values for the asset
     *
     * @return list of zones to set in the asset
     * @throws InvalidParameterException one of the asset values is invalid
     * @throws PropertyServerException there is a problem calculating the zones
     */
    @Override
    public List<String> verifyAssetZones(List<String>  defaultZones,
                                         List<String>  supportedZones,
                                         Asset         originalAsset,
                                         Asset         updatedAsset) throws InvalidParameterException,
                                                                            PropertyServerException
    {
        if (updatedAsset != null)
        {
            if (updatedAsset.getOwner() != null)
            {
                return addZoneName(updatedAsset.getZoneMembership(),
                                   ownerZones.get(updatedAsset.getOwner()));
            }
            else
            {
                return updatedAsset.getZoneMembership();
            }
        }

        return null;
    }


    /**
     * Test to see that the separation of duty rules are adhered to in the quarantine zone.
     *
     * @param userId calling user
     * @param auditHeader audit header of the asset
     * @return boolean flag - true = all in ok
     */
    private boolean validateSeparationOfDuties(String           userId,
                                               AssetAuditHeader auditHeader)
    {
        if (auditHeader != null)
        {
            if (userId.equals(auditHeader.getCreatedBy()))
            {
                return false;
            }

            return true;
        }

        return false;
    }


    /**
     * Tests for whether a specific user should have the right to create an asset within a zone.
     *
     * @param userId identifier of user
     * @throws UserNotAuthorizedException the user is not authorized to access this zone
     */
    @Override
    public void  validateUserForAssetCreate(String     userId,
                                            Asset      asset) throws UserNotAuthorizedException
    {
        if (asset != null)
        {
            if (userHasAccessToAssetZones(userId,
                                          asset.getZoneMembership(),
                                          true,
                                          false,
                                          this.isUserAssetOwner(userId, asset)))
            {
                return;
            }
        }

        /*
         * Any other conditions, use superclass to throw user not authorized exception
         */
        super.validateUserForAssetCreate(userId, asset);
    }


    /**
     * Tests for whether a specific user should have read access to a specific asset within a zone.
     *
     * @param userId identifier of user
     * @throws UserNotAuthorizedException the user is not authorized to access this zone
     */
    @Override
    public void  validateUserForAssetRead(String     userId,
                                          Asset      asset) throws UserNotAuthorizedException
    {
        if (asset != null)
        {
            if (userHasAccessToAssetZones(userId,
                                          asset.getZoneMembership(),
                                          false,
                                          false,
                                          this.isUserAssetOwner(userId, asset)))
            {
                return;
            }
        }

        /*
         * Any other conditions, use superclass to throw user not authorized exception
         */
        super.validateUserForAssetRead(userId, asset);
    }


    /**
     * Tests for whether a specific user should have the right to update an asset.
     * This is used for a general asset update, which may include changes to the
     * zones and the ownership.
     *
     * @param userId identifier of user
     * @param originalAsset original asset details
     * @param originalAssetAuditHeader details of the asset's audit header
     * @param newAsset new asset details
     * @throws UserNotAuthorizedException the user is not authorized to change this asset
     */
    @Override
    public void  validateUserForAssetDetailUpdate(String           userId,
                                                  Asset            originalAsset,
                                                  AssetAuditHeader originalAssetAuditHeader,
                                                  Asset            newAsset) throws UserNotAuthorizedException
    {
        final String methodName = "validateUserForAssetDetailUpdate";
        if (userId != null)
        {
            if ((originalAsset != null) && (newAsset != null))
            {
                if (userHasAccessToAssetZones(userId,
                                              originalAsset.getZoneMembership(),
                                              true,
                                              this.hasZoneChanged(originalAsset.getZoneMembership(),
                                                                  newAsset.getZoneMembership()),
                                              this.isUserAssetOwner(userId, originalAsset)))
                {
                    /*
                     * Do not allow zone to be null
                     */
                    if (newAsset.getZoneMembership() != null)
                    {
                        if (zoneBeenRemoved(quarantineZoneName,
                                            originalAsset.getZoneMembership(),
                                            newAsset.getZoneMembership()))
                        {
                            /*
                             * Perform special processing for quarantine zone.
                             * The owner must be specified
                             */
                            if ((newAsset.getOwner() != null) && (newAsset.getOwnerType() != null))
                            {
                                if (validateSeparationOfDuties(userId, originalAssetAuditHeader))
                                {
                                    return;
                                }
                                else
                                {
                                    super.throwUnauthorizedZoneChange(userId,
                                                                      newAsset,
                                                                      originalAsset.getZoneMembership(),
                                                                      newAsset.getZoneMembership(),
                                                                      methodName);
                                }
                            }
                            else
                            {
                                super.throwIncompleteAsset(userId,
                                                           newAsset,
                                                           methodName);
                            }
                        }
                        else
                        {
                            return;
                        }
                    }
                    else
                    {
                        super.throwIncompleteAsset(userId,
                                                   newAsset,
                                                   methodName);
                    }
                }
            }
        }

        /*
         * Any other conditions, use superclass to throw user not authorized exception
         */
        super.validateUserForAssetDetailUpdate(userId, originalAsset, originalAssetAuditHeader, newAsset);
    }


    /**
     * Tests for whether a specific user should have the right to update elements attached directly
     * to an asset such as glossary terms, schema and connections.
     *
     * @param userId identifier of user
     * @param asset original asset details
     * @throws UserNotAuthorizedException the user is not authorized to change this asset
     */
    @Override
    public void  validateUserForAssetAttachmentUpdate(String     userId,
                                                      Asset      asset) throws UserNotAuthorizedException
    {
        if (asset != null)
        {
            if (userHasAccessToAssetZones(userId,
                                          asset.getZoneMembership(),
                                          true,
                                          false,
                                          this.isUserAssetOwner(userId, asset)))
            {
                return;
            }
        }

        /*
         * Any other conditions, use superclass to throw user not authorized exception
         */
        super.validateUserForAssetAttachmentUpdate(userId, asset);
    }


    /**
     * Tests for whether a specific user should have the right to delete an asset within a zone.
     *
     * @param userId identifier of user
     * @param asset asset details
     * @throws UserNotAuthorizedException the user is not authorized to change this asset
     */
    @Override
    public void  validateUserForAssetDelete(String     userId,
                                            Asset      asset) throws UserNotAuthorizedException
    {
        if (asset != null)
        {
            if (userHasAccessToAssetZones(userId,
                                          asset.getZoneMembership(),
                                          true,
                                          false,
                                          this.isUserAssetOwner(userId, asset)))
            {
                return;
            }
        }

        /*
         * Any other conditions, use superclass to throw user not authorized exception
         */
        super.validateUserForAssetDelete(userId, asset);
    }


    /*
     * =========================================================================================================
     * Type security
     *
     * Any valid user can see the types but only the metadata architects can change them.
     * The logic returns if valid access; otherwise it calls the superclass to throw the
     * user not authorized exception.
     */

    /**
     * Tests for whether a specific user should have the right to create a typeDef within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param typeDef typeDef details
     * @throws UserNotAuthorizedException the user is not authorized to maintain types
     */
    @Override
    public void  validateUserForTypeCreate(String  userId,
                                           String  metadataCollectionName,
                                           TypeDef typeDef) throws UserNotAuthorizedException
    {
        if (metadataArchitects.contains(userId))
        {
            return;
        }

        if (localServerUserId != null)
        {
            if (localServerUserId.equals(userId))
            {
                return;
            }
        }

        super.validateUserForTypeCreate(userId, metadataCollectionName, typeDef);
    }


    /**
     * Tests for whether a specific user should have read access to a specific typeDef within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param typeDef typeDef details
     * @throws UserNotAuthorizedException the user is not authorized to retrieve types
     */
    @Override
    public void  validateUserForTypeRead(String     userId,
                                         String     metadataCollectionName,
                                         TypeDef    typeDef) throws UserNotAuthorizedException
    {
        if (allUsers.contains(userId))
        {
            return;
        }

        if (localServerUserId != null)
        {
            if (localServerUserId.equals(userId))
            {
                return;
            }
        }

        super.validateUserForTypeRead(userId, metadataCollectionName, typeDef);
    }


    /**
     * Tests for whether a specific user should have the right to update a typeDef within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param typeDef typeDef details
     * @throws UserNotAuthorizedException the user is not authorized to maintain types
     */
    @Override
    public void  validateUserForTypeUpdate(String     userId,
                                           String     metadataCollectionName,
                                           TypeDef    typeDef) throws UserNotAuthorizedException
    {
        if (metadataArchitects.contains(userId))
        {
            return;
        }

        super.validateUserForTypeUpdate(userId, metadataCollectionName, typeDef);
    }


    /**
     * Tests for whether a specific user should have the right to delete a typeDef within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param typeDef typeDef details
     * @throws UserNotAuthorizedException the user is not authorized to maintain types
     */
    @Override
    public void  validateUserForTypeDelete(String     userId,
                                           String     metadataCollectionName,
                                           TypeDef    typeDef) throws UserNotAuthorizedException
    {
        if (metadataArchitects.contains(userId))
        {
            return;
        }

        super.validateUserForTypeDelete(userId, metadataCollectionName, typeDef);
    }


    /*
     * =========================================================================================================
     * Instance Security
     *
     * No specific security checks made when instances are written and retrieved from the local repository.
     * The methods override the super class that throws a user not authorized exception on all access/update
     * requests.
     */

    /**
     * Tests for whether a specific user should have the right to create a instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to maintain instances
     */
    @Override
    public void  validateUserForEntityCreate(String       userId,
                                             String       metadataCollectionName,
                                             EntityDetail instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have read access to a specific instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to retrieve instances
     */
    @Override
    public void  validateUserForEntityRead(String          userId,
                                           String          metadataCollectionName,
                                           EntityDetail    instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have read access to a specific instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to retrieve instances
     */
    @Override
    public void  validateUserForEntitySummaryRead(String        userId,
                                                  String        metadataCollectionName,
                                                  EntitySummary instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have read access to a specific instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to retrieve instances
     */
    @Override
    public void  validateUserForEntityProxyRead(String      userId,
                                                String      metadataCollectionName,
                                                EntityProxy instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have the right to update a instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to maintain instances
     */
    @Override
    public void  validateUserForEntityUpdate(String          userId,
                                             String          metadataCollectionName,
                                             EntityDetail    instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have the right to update the classification for an entity instance
     * within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @param classification classification details
     * @throws UserNotAuthorizedException the user is not authorized to maintain instances
     */
    @Override
    public void  validateUserForEntityClassificationUpdate(String          userId,
                                                           String          metadataCollectionName,
                                                           EntityDetail    instance,
                                                           Classification  classification) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have the right to delete a instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to maintain instances
     */
    @Override
    public void  validateUserForEntityDelete(String       userId,
                                             String       metadataCollectionName,
                                             EntityDetail instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have the right to create a instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to maintain instances
     */
    @Override
    public void  validateUserForRelationshipCreate(String       userId,
                                                   String       metadataCollectionName,
                                                   Relationship instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have read access to a specific instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to retrieve instances
     */
    @Override
    public void  validateUserForRelationshipRead(String          userId,
                                                 String          metadataCollectionName,
                                                 Relationship    instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have the right to update a instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to maintain instances
     */
    @Override
    public void  validateUserForRelationshipUpdate(String          userId,
                                                   String          metadataCollectionName,
                                                   Relationship    instance) throws UserNotAuthorizedException
    {
    }


    /**
     * Tests for whether a specific user should have the right to delete a instance within a repository.
     *
     * @param userId identifier of user
     * @param metadataCollectionName configurable name of the metadata collection
     * @param instance instance details
     * @throws UserNotAuthorizedException the user is not authorized to maintain instances
     */
    @Override
    public void  validateUserForRelationshipDelete(String       userId,
                                                   String       metadataCollectionName,
                                                   Relationship instance) throws UserNotAuthorizedException
    {
    }
}
