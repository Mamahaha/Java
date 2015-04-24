namespace('bvps.custom.i18n', function() {
    this.ResourceBundle = {
        en_US: {
            en_US: 'English',
//            sv_SE: 'Svenska',
            tailf: {
                'brandName': 'BMC',
                'webTitle': 'BMC',
                'login.title': 'Welcome to Broadcast Management Control',
                'breadcrumbsHome': 'Home',
                '%s has non-unique members.':'The list "%s" already contains %s',
                'tx.msg.validationError':'Error',
                'tx.confirm.exit': 'Do you want to cancel your changes?',
                'tx.confirm.exit.unsaved': 'You have %s unsaved changes!'

            },
            'ncs.common': {
                'dashboard': 'Devices'
            },
            'eventlist':{
                'event-list-page': 'Event List',
                'event': "Event List"
            },
            /*,
            // Example translations
            ncs: 'NCS',
            al: 'Alarm Center',
            nm: 'Netconf Manager',
            ncs_snmp: 'NCS SNMP',
            snmp: 'SNMP',
            sm: 'Service manager',

            // Example descriptions
            '/ncs:devices/global-settings/connect-timeout': {
                'webui:description': 'Foo bar baz'
            },
            '/ncs:devices/global-settings/read-timeout': {
                'webui:description': 'url:unversioned/popover-description.html'
            },
            '/ncs:services': {
                'webui:description': 'url:unversioned/popover-description.html'
            },
            '/ncs:devices/global-settings/trace': {
                'webui:description': 'url:unversioned/popover-description.html'
            },
            '/ncs:devices/global-settings/backlog-enabled': {
                'webui:description': 'url:unversioned/popover-description.html'
            }*/
            'bvps':{
                'helper.btn.remove.tooltip': 'Remove the %s',
                'broadcast.table.delete.confirm.ok':      'Yes',
                'broadcast.table.delete.confirm.cancel':  'No',
                'broadcast.table.delete.confirm.title':   'Remove broadcast?',
                'broadcast.table.delete.confirm.message': '%s is %s and contains %s contents, do you want to continue?',//siblings[1].innerHTML.trim()/*name*/, siblings[5].innerHTML.trim()/*status*/

                'servicemonitor.alarmnotification.popup.noalarms': 'No alarms were found for this broadcast',
                'servicemonitor.alarmnotification': 'Show latest alarm',
                'servicearea.frequency.onefrequencyerror.message': 'You must specify either two frequencies or no frequency.'
            },

            '/ncs:services/service':{
                 'service': 'Services',
                 'mdf-ncm': 'MDF NCM',
                 'info': 'A list of broadcast services'
            },
            'sa': 'Service Announcement',
            'embms': 'EMBMS',
            'aaa': 'Authentication and Authorization',
            'ncs': 'NCS',
            'fec':'FEC',
            'fec-encoding-id':'Encoding ID',
            'fec-overhead':'Overhead (&#37;)',
            'encoding-symbol-length':'Encoding Symbol Length (bytes)',
            'recommended-fec-overhead': 'Recommended FEC Overhead (&#37;)',
            'offset-time': 'Offset Time (s)',
            'random-time-window': 'Random Time Window (s)',
            'sample-percent': 'Sample Percent (&#37;)',
            'msdc-service': 'MSDC Service',
            'allocated-bandwidth':'Allocated bandwidth (bit/s)',
            'bmsc':'BMSC',
            'tmgi':'TMGI',
            'gbr':'Guaranteed Bit Rate (kbit/s)',
            'max-bitrate': 'Max Bitrate (kbit/s)',
            'file-uri': 'File URI',
            'mpd':'MPD',
            'mpd-and-is-reference':'MPD And IS Reference',
            'mpd-modification-enabled': 'MPD Time Adjustment Enabled',
            'fqdn':'FQDN',
            'fragment-url':'Fragment URL',
            'bmsc-tear-down-time': 'Bmsc Tear Down Time (s)',
            'ue-tune-in-time': 'UE Tune In Time (s)',
            'cancel-wait-time': 'Cancel Wait Time (s)',
            'file-download-transmit-time': 'File Download Transmit Time (s)',
            'valid-until-timer': 'Valid Until Timer (m)',
            'valid-until-frequency': 'Valid Until Frequency (m)',
            'http-client-time-out':'Http Client Time Out (s)',
            'file-delivery-tmgi-activation-retry-time-out':'File Delivery TMGI Activation Retry Time Out (s)',
            'file-delivery-oos-tmgi-activation-retry-time-out': 'File Delivery Oos Tmgi Activation Retry Time Out (s)',
            'mpd-default-modification-parameters': 'MPD Default Modification Parameters',
            'mpd-modification-parameters':'MPD Modification Parameters',
            //////////////
            // System Settings
            //////////////
            '/ncs:services/properties/settings/base-url':{
                'base-url': 'Base URL',
                'webui:description': 'The system base URL used to generate URLs in the Service Announcement file.'
            },
            '/ncs:services/properties/settings/base-urn':{
                'base-urn': 'Base URN',
                'webui:description': 'The system base URN used to generate URNs in the Service Announcement file.'
            },
            '/ncs:services/properties/settings/valid-until-timer':{
                'valid-until-timer': 'Valid Until Timer (m)',
                'webui:description': 'Value for increment of valid until timer. Value in minutes. The minimum value for Valid Until Frequency is 1, which means that Valid Until Timer must be at least 2.<br>Default value 2880 minutes = 48 hours'
            },
            '/ncs:services/properties/settings/valid-until-frequency':{
                'valid-until-frequency': 'Valid Until Frequency (m)',
                'webui:description': 'Value for update frequency of the valid until timer. Value in minutes.<br>Default value 1440 minutes = 24 hours'
            },
            '/ncs:services/properties/settings/initial-valid-until-frequency':{
                'initial-valid-until-frequency': 'Initial Valid Until Frequency (m)',
                'webui:description': 'Value of the first update, in minutes, after system start to trigger valid until timer.<br>Default value 5 minutes'
            },
            '/ncs:services/properties/settings/broadcast-service/default-control-fragment-repetition-interval':{
                'default-control-fragment-repetition-interval': 'Default Control Fragment Repetition Interval (s)',
                'webui:description': 'The time between sending Control Fragment to BMSC.'
            },
            '/ncs:services/properties/settings/broadcast-service/default-fdt-repetition-period':{
                'default-fdt-repetition-period': 'Default FDT Repetition Period (hundreds of milliseconds)',
                'webui:description': 'The File Download Transmit repetition period used by the BMSC for all content transmitted within the delivery instance id.'
            },
            '/ncs:services/properties/settings/broadcast-service/bmsc-set-up-time':{
                'bmsc-set-up-time': 'BMSC Set-up Time (s)',
                'webui:description': 'The time it takes for the BMSC to make the eMBMS session ready for delivery of content.'
            },
            '/ncs:services/properties/settings/broadcast-service/bmsc-tear-down-time': {
            	'bmsc-tear-down-time': 'BMSC Tear Down Time (s)',
            	'webui:description': 'The time it takes for the BMSC to clean up an eMBMS session after its stop time.'
            },
            '/ncs:services/properties/settings/broadcast-service/ue-tune-in-time': {
            	'ue-tune-in-time': 'UE Tune In Time (s)',
            	'webui:description': 'The time needed for a UE to tune in to a broadcast before a streaming or file content is started.'
            },
            '/ncs:services/properties/settings/broadcast-service/cancel-wait-time': {
            	'cancel-wait-time': 'Cancel Wait Time (s)',
            	'webui:description': 'The time, in seconds, before a cancelled broadcast is set to pending.'
            },
            '/ncs:services/properties/settings/broadcast-service/mpd-wait-timer': {
            	'mpd-wait-timer': 'MPD Wait Timer (m)',
            	'webui:description': 'The time in minutes before a broadcast starts that the MPD must be present.If the MPD not is present before "Broadcast Start Time - MPD Wait Time",an MPD Missing Alarm will be generated.'
            },
            '/ncs:services/properties/settings/broadcast-service/bearer-activated-wait-time': {
            	'bearer-activated-wait-time': 'Bearer Activated Wait Time (s)',
            	'webui:description': 'Specifies in seconds, how long before the broadcast start time the system starts to check whether the service bearer is activated. If the service bearer is not activated, the system will issue the "bearer-activated-missing-alarm" alarm.'
            },
            '/ncs:services/properties/settings/broadcast-service/file-download-transmit-time': {
            	'file-download-transmit-time': 'File Download Transmit Time (s)',
            	'webui:description': 'The time in advance that a file download must be queued so that the file is broadcast on the announced time.'
            },
            '/ncs:services/properties/settings/broadcast-service/max-expired-pending-broadcast-per-service': {
            	'webui:description': 'The max number of expired pending broadcast in one service. -1 means to do nothing for expired pending broadcast, Otherwise BMC will remove the oldest broadcast if the total number of expired pending broadcast exceed the defined value. 0 means no expired pending broadcast is allowed, however it is not recommended in product environment.'
            },
            '/ncs:services/properties/settings/cdn/poll-interval': {
                'poll-interval':'Poll Interval (m)',
            	'webui:description': 'Time, in minutes, to wait between attempts at retrieving files.'
            },
            '/ncs:services/properties/settings/cdn/mpd-poll-interval': {
                'mpd-poll-interval':'MPD Poll Interval (s)',
            	'webui:description': 'Time (in seconds) to wait between attempts at retrieving MPD files from CDN/DMF.'
            },
            '/ncs:services/properties/settings/cdn/saf-path': {
            	'saf-path': 'SAF Path',
            	'webui:description': 'The path in which BVPS will write the service announcement file on the SFTP server.'
            },
            '/ncs:services/properties/settings/cdn/msdc-path': {
            	'msdc-path': 'MSDC Path',
            	'webui:description': 'The path in which BVPS will write the MSDC file on the SFTP server.'
            },
            '/ncs:services/properties/settings/cdn/boot-strap-path': {
            	'boot-strap-path': 'Bootstrap Path',
            	'webui:description': 'The path in which BVPS will write the Bootstrap file on the SFTP server.'
            },
            '/ncs:services/properties/settings/cdn/mmwc-path': {
            	'mmwc-path': 'MW Config Path',
            	'webui:description': 'The path in which BVPS will write the Middleware configuration file on the SFTP server.'
            },
            '/ncs:services/properties/settings/cdn/boot-config-path': {
            	'boot-config-path': 'Bootconfig Path',
            	'webui:description': 'The path in which BVPS will write the Bootconfig file on the SFTP server.'
            },
            '/ncs:services/properties/settings/cdn/combination': {
            	'combination': 'Bootconfig Generation',
            	'webui:description': 'Specify whether BMC should combine Bootstrap file and MMW configuration file into one Multipart MIME file.'
            },

            '/ncs:services/properties/settings/broadcast-service/mpd-default-modification-parameters/time-shift-buffer-depth':{
                'time-shift-buffer-depth': 'Time Shift Buffer Depth (s)',
                'webui:description': 'Specifies the duration of the time shifting buffer that is guaranteed to be available for a Media Presentation with type "dynamic".'
            },
            '/ncs:services/properties/settings/reporting/host':{
                'host': 'Host',
                'webui:description': 'reporting node hostname or IP address.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/settings/reporting/port':{
                'port': 'Meta-data Port',
                'webui:description': 'reporting node port for push meta-data.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/settings/reporting/gui-port':{
                'gui-port': 'GUI Port',
                'webui:description': 'reporting node port for access reporting GUI.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/settings/reporting/domain':{
                'domain': 'GUI Domain Name',
                'webui:description': 'reporting node domain for GUI.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/settings/reporting/retry-interval':{
                'retry-interval': 'Retry Interval (s)',
                'webui:description': 'retry interval of push meta-data.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/settings/reporting/retry-times':{
                'retry-times': 'Retry times',
                'webui:description': 'retry times of push meta-data if push failed.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/settings/reporting-failed-metadata/max-metadata':{
                'max-metadata': 'max saved meta-data',
                'webui:description': 'max saved failed meta-data.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/settings/license/license-expire-warning-threshold':{
                'license-expire-warning-threshold': 'license expire warning raise before threshold (days)',
            },
            '/ncs:services/properties/settings/device-connection/virtual-traffic-address':{
                'webui:description': 'The virtual traffic IP address of BMC in HA solution. This address is used as local bind address by BMC to communicate with BDC devices, other local address of BMC will be rejected by the iptables of BDC devices in HA solution.',
            },
            '/ncs:services/properties/settings/device-connection/virtual-management-address':{
                'webui:description': 'The virtual management IP address of BMC in HA solution. This address is used as local bind address by BMC to communicate with NCM devices or Reporting server, other local address of BMC will be rejected by the iptables of NCM devices or Reporting server in HA solution.',
            },
//////////////
            // UE Config
            //////////////
            '/emw:msdc-config-params/uni-cast/http-retry-attempts': {
                'http-retry-attempts': 'HTTP Retry Attempts',
                'webui:description': 'The number of times the middleware will retry to connect to the server.'
            },
            '/emw:msdc-config-params/uni-cast/http-retry-period': {
                'http-retry-period': 'HTTP Retry Period (s)',
                'webui:description': 'Back-off period between consecutive retry attempts.'
            },
            '/emw:msdc-config-params/bearer-control/file-delivery-tmgi-activation-retry-time-out': {
                'file-delivery-tmgi-activation-retry-time-out': 'File Delivery TMGI Activation Retry Time Out (s)',
                'webui:description': 'The periodic interval for requesting TMGI re-activation from the modem for file delivery type of services.'
            },
            '/emw:msdc-config-params/bearer-control/file-delivery-oos-tmgi-activation-retry-time-out': {
                'file-delivery-oos-tmgi-activation-retry-time-out': 'File Delivery Out-Of-Service TMGI Activation Retry Time Out (s)',
                'webui:description': 'The periodic interval for requesting TMGI activation from the modem under Out-Of-Service conditions for file delivery type of services.'
            },
            '/emw:msdc-config-params/streaming/segment-proc-delayed-base': {
                'segment-proc-delayed-base' : 'Segment Processing Delay Base (ms)',
                'webui:description': 'Control processing delay of DASH media segments: Latency independent of segment sizes'
            },
            '/emw:msdc-config-params/streaming/segment-proc-delayed-factor': {
                'segment-proc-delayed-factor': 'Segment Processing Delay Factor',
                'webui:description': 'Control processing delay of DASH media segments: Latency proportional to the segment size'
            },
            '/emw:msdc-config-params/service-announcement/service-discovery-wakeup-duration': {
                'service-discovery-wakeup-duration': 'Service Discovery Wake up Duration (m)',
                'webui:description': 'Duration at which service discovery module wakes up and looks for announcement updates.'
            },
            '/emw:msdc-config-params/service-announcement/usd-file-size-quota': {
                'usd-file-size-quota': 'User Service Description File Size Quota (bytes)',
                'webui:description': 'The amount of space required to store multipart MIME file containing the service definitions.'
            },
            '/emw:msdc-config-params/service-announcement/configuration-file-size-quota': {
                'configuration-file-size-quota': 'Configuration File Size Quota (bytes)',
                'webui:description': 'The amount of space required to store configuration XML file.'
            },
            '/emw:msdc-config-params/service-announcement/registration-validity': {
                'registration-validity': 'Application Registration Validity Timeout (h)',
                'webui:description': 'Timeout which controls the how long the MSDC will hold the registration data when the application is not active.'
            },
            '/emw:msdc-config-params/service-announcement/sd-file-receive-wait-time': {
                'sd-file-receive-wait-time': 'SD File Receive Wait Time (s)',
                'webui:description': 'How long Service Discovery module should wait for any file to be received from DDF.'
            },
            '/emw:msdc-config-params/file-download/file-repair-threshold': {
                'file-repair-threshold': 'File Repair Threshold',
                'webui:description': 'Threshold relative to the file size defining a limit on the amount of symbols to request for file repair.'
            },
            '/emw:msdc-config-params/file-download/full-file-repair-enable': {
                'file-repair-threshold': 'Full File Repair',
                'webui:description': 'Controls use of FileRepairThreshold.<br>– Enabled: For file repairs requiring more symbols than FileRepairThreshold the middleware request for the complete file.<br>– Disabled: File repairs requiring more symbols than FileRepairThreshold of the file size are aborted.'
            },
            '/emw:msdc-config-params/file-download/advance-wakeup-for-files': {
                'advance-wakeup-for-files': 'Advance Wakeup For Files (s)',
                'webui:description': 'How many seconds in advance the UE shall wakeup before a file download.'
            },
            '/emw:msdc-config-params/file-download/unfinished-file-download-quota': {
                'unfinished-file-download-quota': 'Unfinished File Download Quota (bytes)',
                'webui:description': 'The amount of storage reserved for storing files that are still being downloaded partially but not decoded or downloaded completely.'
            },
            '/emw:msdc-config-params/flute-session/min-bytes-until-fdt-available': {
                'min-bytes-until-fdt-available': 'Minimum Bytes Until File Delivery Table Available (bytes)',
                'webui:description': 'How many minimum bytes of packets with Transport Object Identifier (TOI) to store before the TOIs of these packets appear in FDT for a session.'
            },
            '/emw:msdc-config-params/flute-session/number-of-ftds': {
                'number-of-ftds': 'Number Of File Delivery Tables',
                'webui:description': 'Minimum number of File Delivery Tables the middleware stores for a FLUTE session.'
            },
            '/emw:msdc-config-params/reception-reporting/reception-reporting-quota': {
                'reception-reporting-quota': 'Reception Reporting Quota (bytes)',
                'webui:description': 'The amount of storage needed to be reserved for collection of reception reports by the UEs.'
            },
            //////////////
            // MMWC Config
            //////////////
            '/mmwc:multicast-middleware-configuration/mmw-configuration-file': {
                'mmw-configuration-file': 'Configuration File'
            },
            '/mmwc:multicast-middleware-configuration/mmw-schema': {
                'mmw-schema': 'Configuration Schema'
            },
            '/mmwc:multicast-middleware-configuration/mmw-schema/schema-name': {
                'schema-name': 'Schema Name'
            },
            '/mmwc:multicast-middleware-configuration/mmw-schema/schema-content': {
                'schema-content': 'Schema Content'
            },
            /////////////////////
            // end of MMWC Config
            /////////////////////

            '/ncs:services/properties/settings/broadcast-service/qoe-metrics-template/resolution':{
                'resolution': 'Resolution (s)',
                'info': 'More than 1 second'
            },
            '/ncs:services/service/type/broadcast-service/service-bearer/user-service/qoe-metrics/resolution':{
                'resolution': 'Resolution (s)',
                'info': 'More than 1 second'
            },

            '/ncs:services/service/type/broadcast-service/service-bearer/user-service/qoe-metrics/range':{
                'range': 'Range',
                'webui:description': '<span style="font-size: 75%"> Range: clock=19960213T143205Z-;time=19970123T143720Z <br>'+
                        'utc-range    =   "clock" "=" utc-time "-" [ utc-time ] <br>'+
                        'utc-time     =   utc-date "T" utc-time "Z" <br>'+
                        'utc-date     =   8DIGIT                    ; < YYYYMMDD > <br>'+
                        'utc-time     =   6DIGIT [ "." fraction ]   ; < HHMMSS.fraction > <br>'+
                        'Example for November 8, 1996 at 14h37 and 20 and a quarter seconds <br>'+
                        'UTC: <br>'+
                        '19961108T143720.25Z <br></span>'
            },

            '/ncs:services/properties/settings/broadcast-service/qoe-metrics-template/range':{
                'range': 'Range',
                'webui:description': '<span style="font-size: 75%">Range: clock=19960213T143205Z-;time=19970123T143720Z <br>'+
                        'utc-range    =   "clock" "=" utc-time "-" [ utc-time ] <br>'+
                        'utc-time     =   utc-date "T" utc-time "Z" <br>'+
                        'utc-date     =   8DIGIT                    ; < YYYYMMDD > <br>'+
                        'utc-time     =   6DIGIT [ "." fraction ]   ; < HHMMSS.fraction > <br>'+
                        'Example for November 8, 1996 at 14h37 and 20 and a quarter seconds <br>'+
                        'UTC: <br>'+
                        '19961108T143720.25Z <br></span>'
            },

            'sftp-config': 'SFTP Config',
            'sftp-config2': 'SA SFTP Config',
            'cdn':'Content Delivery Network',
            'msdc-file-name':'MSDC File Name',
            'poll-interval':'Poll Interval (m)',
            'boot-strap-file-name':'Bootstrap File Name',
            'boot-strap-path':'Bootstrap Path',
            'mmwc-file-name':'MW Config File Name',
            'boot-config-file-name':'Bootconfig File Name',
            'multicast-middleware-configuration':'Middleware Configuration',
            'saf-upload-interval':'SAF Upload Interval (s)',
            'lte-radio-frequency':'LTE Radio Frequency',
            'frequency':'Frequency (EARFCN)',
            'qoe-metrics':'QoE Metrics',
            'dash-nw-resource': 'DASH Network Resource',
            'dash-loss-of-objects': 'DASH Loss Of Objects',
            'msdc-config-params': 'User Equipment Configuration',
            'info/network-state': 'Network State',
            'type/broadcast-service/description': 'Description',
            'type/broadcast-service/broadcast-area': 'Broadcast area',
            'type/broadcast-service/announce': 'Announce',
            'characteristics/recommended-fec-overhead': 'Recommended FEC Overhead (&#37;)',
            '../../../object-id': 'Broadcast Service',
            'impacted-managed-object': 'Related Object',
            'last-perceived-severity': 'Severity',
            'is-cleared': 'Cleared',
            'last-status-change': 'Changed',
            'last-alarm-text': 'Alarm Text',
						'http-client-time-out': 'HTTP Client Time Out ',
            '/ncs:services/properties/service-areas/service-area/region': {
                region: 'Coverage'
            },
            '/ncs:services/properties/service-areas/service-area/user': {
                user: 'Upcoming broadcasts'
            },
            '/ncs:services/service/type/broadcast-service/service-bearer/user-service/content': {
                'on-demand-v': 'File media',
                'continuous-v': 'Streaming media'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content': {
                'on-demand-v': 'File media',
                'continuous-v': 'Streaming media'
            },
            '/ncs:services/properties/service-areas/service-area/info/over-lapping-sai':{
                'over-lapping-sai': 'Overlapping SAI'
            },
            '/ncs:services/properties/service-areas/service-area/availability-info': {
                'availability-info': 'Frequency'
            },
            '/ncs:services/properties/settings/availability-info': {
                'availability-info': 'Frequency'
            },
            '/ncs:services/properties/broadcast-areas/broadcast-area/region': {
                    region: 'Coverage'
            },
            'schedule/end-time': 'End time',
            'schedule/start-time/announced': 'Start Time',
            'schedule/stop-time/announced': 'Stop Time',
            'schedule/stop-time/actual': 'Stop Time',
            '/ncs:services/service/type/broadcast-service/broadcast/schedule/start-time/announced':{
                'announced':'Announced Start Time'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/schedule/start-time/actual':{
                'actual':'Actual Start Time'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/schedule/stop-time/announced':{
                'announced':'Announced Stop Time'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/schedule/stop-time/actual':{
                'actual':'Actual Stop Time'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/on-demand': {
                    'on-demand': 'File',
                    'continuous': 'Repeated'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/on-demand/once/on-demand-file':{
                'on-demand-file': 'File List'
            },
            'file-reporting-template' : 'Reception Reporting Template',
            '/ncs:services/service/type/broadcast-service/broadcast/content/file-reporting':{
                'file-reporting': 'Reception Reporting'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/file-reporting-cache/file-reporting':{
                'file-reporting': 'Reception Reporting Cache'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/on-demand/once/file-repair-cache/file-repair':{
                'file-repair': 'File Repair Cache'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/continuous/metadata/is':{
                'is': 'Initialization Segment'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/on-demand/repeat': {
                    'repeat': 'Send As'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/on-demand/continuous': {
                    'continuous': 'Repeated'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/continuous': {
                    'continuous': 'Stream'
            },
            '/opreg:operators/operator/location/state-region':{
                'state-region': 'State or Region'
            },
            '/opreg:operators':{
                'operators': 'User Administration',
                'info': 'Manage the system users and their roles'
            },
            '/ncs:services/properties/settings/ui-settings/time-zone': {
                    'UTC'    : 'UTC'   ,
                    'UTC+1'  : 'UTC + 1' ,
                    'UTC+2'  : 'UTC + 2' ,
                    'UTC+3'  : 'UTC + 3' ,
                    'UTC+4'  : 'UTC + 4' ,
                    'UTC+5'  : 'UTC + 5' ,
                    'UTC+6'  : 'UTC + 6' ,
                    'UTC+7'  : 'UTC + 7' ,
                    'UTC+8'  : 'UTC + 8' ,
                    'UTC+9'  : 'UTC + 9' ,
                    'UTC+10' : 'UTC + 10',
                    'UTC+11' : 'UTC + 11',
                    'UTC+12' : 'UTC + 12',
                    'UTC-14' : 'UTC - 14',
                    'UTC-13' : 'UTC - 13',
                    'UTC-12' : 'UTC - 12',
                    'UTC-11' : 'UTC - 11',
                    'UTC-10' : 'UTC - 10',
                    'UTC-9'  : 'UTC - 9' ,
                    'UTC-8'  : 'UTC - 8' ,
                    'UTC-7'  : 'UTC - 7' ,
                    'UTC-6'  : 'UTC - 6' ,
                    'UTC-5'  : 'UTC - 5' ,
                    'UTC-4'  : 'UTC - 4' ,
                    'UTC-3'  : 'UTC - 3' ,
                    'UTC-2'  : 'UTC - 2' ,
                    'UTC-1'  : 'UTC - 1',
                    'GMT'    : 'GMT'   ,
                    'GMT+0100' : 'GMT +0100',
                    'GMT+0200' : 'GMT +0200',
                    'GMT+0300' : 'GMT +0300',
                    'GMT+0400' : 'GMT +0400',
                    'GMT+0500' : 'GMT +0500',
                    'GMT+0600' : 'GMT +0600',
                    'GMT+0700' : 'GMT +0700',
                    'GMT+0800' : 'GMT +0800',
                    'GMT+0900' : 'GMT +0900',
                    'GMT+1000' : 'GMT +1000',
                    'GMT+1100' : 'GMT +1100',
                    'GMT+1200' : 'GMT +1200',
                    'GMT-1400' : 'GMT -1400',
                    'GMT-1300' : 'GMT -1300',
                    'GMT-1200' : 'GMT -1200',
                    'GMT-1100' : 'GMT -1100',
                    'GMT-1000' : 'GMT -1000',
                    'GMT-0900' : 'GMT -0900',
                    'GMT-0800' : 'GMT -0800',
                    'GMT-0700' : 'GMT -0700',
                    'GMT-0600' : 'GMT -0600',
                    'GMT-0500' : 'GMT -0500',
                    'GMT-0400' : 'GMT -0400',
                    'GMT-0300' : 'GMT -0300',
                    'GMT-0200' : 'GMT -0200',
                    'GMT-0100' : 'GMT -0100'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/continuous/metadata/mpd-modification-parameters/alpha':{
                'alpha': 'Alpha (factor)',
                'webui:description':
                    'Factor which is multiplied with the Minimum Buffer Time.<br>'+
                    'Alpha and Beta is used as factors in the following formula:<br>'+
                    '@availabilityStartTimeUpdated = availabilityStartTimeOriginal + alpha * Minimum Buffer Time + End To End Network Delay + beta*duration<br><br>'+
                    'These empirical factors are used to dynamically modify the availability start time in a network depending on the different network delays.'
            },
            '/ncs:services/properties/settings/broadcast-service/mpd-modification-parameters/alpha':{
                'alpha': 'Alpha (factor)',
                'webui:description':
                    'Factor which is multiplied with the Minimum Buffer Time.<br>'+
                    'Alpha and Beta is used as factors in the following formula:<br>'+
                    '@availabilityStartTimeUpdated = availabilityStartTimeOriginal + alpha * Minimum Buffer Time + End To End Network Delay + beta*duration<br><br>'+
                    'These empirical factors are used to dynamically modify the availability start time in a network depending on the different network delays.'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/content/continuous/metadata/mpd-modification-parameters/beta':{
                'beta': 'Beta (factor)',
                'webui:description':
                    'Factor which is multiplied with the Duration.<br>'+
                    'Alpha and Beta is used as factors in the following formula:<br>'+
                    '@availabilityStartTimeUpdated = availabilityStartTimeOriginal + alpha * Minimum Buffer Time + End To End Network Delay + beta*duration<br><br>'+
                    'These empirical factors are used to dynamically modify the availability start time in a network depending on the different network delays.'
            },
            '/ncs:services/properties/settings/broadcast-service/mpd-modification-parameters/beta':{
                'beta': 'Beta (factor)',
                'webui:description':
                    'Factor which is multiplied with the Duration.<br>'+
                    'Alpha and Beta is used as factors in the following formula:<br>'+
                    '@availabilityStartTimeUpdated = availabilityStartTimeOriginal + alpha * Minimum Buffer Time + End To End Network Delay + beta*duration<br><br>'+
                    'These empirical factors are used to dynamically modify the availability start time in a network depending on the different network delays.'
            },
            '/ncs:devices/device/sync':{
                'sync': 'Synchronize',
                'info': 'Synchronize the configs by pushing to device'
            },
            '/ncs:devices/device/ADF-DB-interval':{
                'ADF-DB-interval': 'Download Delta time'
            },
            '/ncs:devices/device/ADF-DB-interval/interval-time':{
                'interval-time': 'Delay Time (m)',
                'webui:description': 'This is the delay time in minutes for BMC reporting server download files from ADF DB server each time.'
            },
            '/ncs:services/properties/bdc-devices/bdc-device/ADF-DB-interval':{
                'ADF-DB-interval': 'Download Delta time'
            },
            '/ncs:services/properties/bdc-devices/bdc-device/ADF-DB-interval/interval-time':{
                'interval-time': 'Delay Time (m)',
                'webui:description': 'This is the delay time in minutes for BMC reporting server download files from ADF DB server each time.'
            },
            '/ncs:services/properties/bdc-devices/bdc-device/ADF-DB-SFTP-configuration':{
                'ADF-DB-SFTP-configuration': 'ADF SFTP configuration'
            },
            '/ncs:services/properties/bdc-devices/bdc-device/ADF-DB-SFTP-configuration/address':{
                'address': 'Address',
                'webui:description': 'IPv4 address or IPv6 address or host name or FQDN for ADF sftp server.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/bdc-devices/bdc-device/ADF-DB-SFTP-configuration/port':{
                'port': 'Port',
                'webui:description': 'Port for the ADF sftp server.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/bdc-devices/bdc-device/ADF-DB-SFTP-configuration/base-folder':{
                'base-folder': 'Base Folder',
                'webui:description': 'Base folder specifies path that stores the reporting logs on the ADF-DB node.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/bdc-devices/bdc-device/ADF-DB-SFTP-configuration/username':{
                'username': 'User Name',
                'webui:description': 'User name for the ADF sftp server.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            '/ncs:services/properties/bdc-devices/bdc-device/ADF-DB-SFTP-configuration/password':{
                'password': 'Password',
                'webui:description': 'Password for the ADF sftp server.<br>'+
				'It is used for reporting feature, If you don\'t have reporting license, this field will not take effect.'
            },
            //
            '/ncs:devices/device/ADF-DB-SFTP-configuration':{
                'ADF-DB-SFTP-configuration': 'ADF SFTP configuration'
            },
            '/ncs:devices/device/ADF-DB-SFTP-configuration/address':{
                'address': 'Address',
                'webui:description': 'IPv4 address or IPv6 address or host name or FQDN for ADF sftp server'
            },
            '/ncs:devices/device/ADF-DB-SFTP-configuration/port':{
                'port': 'Port',
                'webui:description': 'Port for the ADF sftp server'
            },
            '/ncs:devices/device/ADF-DB-SFTP-configuration/base-folder':{
                'base-folder': 'Base Folder',
                'webui:description': 'Base folder path in the ADF sftp server'
            },
            '/ncs:devices/device/ADF-DB-SFTP-configuration/username':{
                'username': 'User Name',
                'webui:description': 'User name for the ADF sftp server'
            },
            '/ncs:devices/device/ADF-DB-SFTP-configuration/password':{
                'password': 'Password',
                'webui:description': 'Password for the ADF sftp server'
            },
            //
            '/ncs:services/properties/settings/broadcast/default-broadcast-duration':{
                'default-broadcast-duration': 'Default Broadcast Duration (m)',
                'webui:description': 'This is the time which is added to the broadcast start time to determine the broadcast stop time.'
            },
            '/ncs:services/properties/settings/broadcast/additional-stop-time':{
                'additional-stop-time': 'Stop Time Addition (m)',
                'webui:description': 'This is the time which is added to the broadcast stop time to determine the service stop time, the time where the network resources are released.'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/schedule/broadcast-duration':{
                'webui:description': 'The duration of which the Content will be sent in this Broadcast.<br>The network resources will not be released at Stop Time but after the Extension Stop Time.'
            },
            '/ncs:services/service/type/broadcast-service/broadcast/schedule/max-extension-duration':{
                'webui:description': 'The maximum extent of time which the broadcast can be extended.<br>Caution: The network resources are not released until this time has expired.'
            },
            'contact/email-address':'Email Address',
            'contact/cellular-number':'Cellular Number',

            ///////////////////////
            // Network Planning
            //////////////////////
            '/ncs:services/service/type/bdc-ncm-service':{
                 'bdc-mdf-ncm': 'BDC Device',
                 'service': 'BDC Configuration'
            },
            '/ncs:services/service/type/bdc-ncm-service/adm-state':{
                 'adm-state': 'Admin State',
                 'webui:description': 'The administrative status of the BDC Configuration Service. <br><b>Pending</b>: The configuration will be saved but not sent to the device. <br><b>Approved</b>: The configuration will be saved and sent to the device.'
            },
            '/ncs:services/service/type/bdc-ncm-service/bdc-mdf-ncm':{
                 'webui:description': 'The administrative status of the BDC Configuration Service. <br><b>Pending</b>: The configuration will be saved but not sent to the device. <br><b>Approved</b>: The configuration will be saved and sent to the device.'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/common-conf':{
                 'common-conf': 'Common Parameter',
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/common-conf/inner-source-ip':{
                 'inner-source-ip': 'Inner Source IP',
                 'webui:description': 'Specifies the source IP address of the eMBMS delivery session. The IP version of the source IP address must be aligned with the value of the IPAddressType parameter. The value also needs to be the same as the value of the FLUTE/InnerSourceIp parameter in the /etc/mdf-up.conf file on the MDF-UP. <br><b>Note</b>:</b> It affects the value of the SsmIp parameter in MDF-CP and the value of the FLUTE/InnerSourceIp parameter in MDF-UP.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/common-conf/inner-source-port':{
                 'inner-source-port': 'Inner Source Port',
                 'webui:description': 'Specifies the source port of the eMBMS delivery session. The value needs to be the same as the value of FLUTE/InnerSourcePort parameter in the /etc/mdf-up.conf file on the MDF-UP. <br><b>Note</b>:</b> It affects the value of the SsmPort parameter in MDF-CP and the value of theFLUTE/InnerSourcePort parameter in MDF-UP.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/common-conf/inner-mtu':{
                 'inner-mtu': 'Inner MTU',
                 'webui:description': 'Specifies the Maximum Transmission Unit (MTU) for inner IP packets. To avoid IP fragmentation, the size of the inner IP packets, which include the payload, FLUTE headers, inner UDP headers, and inner multicast IP headers, must not exceed the value of this parameter. Note: The SYNC headers are not included in the size of inner IP packets. The value is in bytes.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/common-conf/raptor-alignment':{
                 'raptor-alignment': 'Raptor Alignment',
                 'webui:description': 'Specifies the Raptor10 symbol alignment parameter. Symbol and sub-symbol sizes are restricted to be multiples of this value. The symbol alignment factor value must be a multiple of four. The value is in bytes.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/common-conf/raptorq-alignment':{
                 'raptorq-alignment': 'RaptorQ Alignment',
                 'webui:description': 'Specifies the RaptorQ symbol alignment parameter. Symbol and sub-symbol sizes are restricted to be multiples of this value. The symbol alignment factor value must be a multiple of four. The value is in bytes.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sync-conf/max-msp':{
                 'max-msp': 'Max Msp',
                 'webui:description': 'Specifies the maximum value of the MSP that is configured in the eNB. The value is in milliseconds. It must be the synchronization sequence length or a multiple of the synchronization sequence length for MBMS services in the MCH.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/common-conf/transmission-timeoffset':{
                 'transmission-timeoffset': 'Transmission Time Offset',
                 'webui:description': 'Specifies the time between when FLUTE packets are sent from the MDF-UP and arrive at the eNBs. The value is in milliseconds.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf':{
                 'sesson-conf': 'Session Management Parameters'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/mcc':{
                 'mcc': 'MCC',
                 'webui:description': 'Specifies the Mobile Country Code, which is included as a part of TMGI.<br><b>Value Range</b>:000–999',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/mnc':{
                 'mnc': 'MNC',
                 'webui:description': 'Specifies the Mobile Network Code, which is included as a part of TMGI.<br><b>Value Range</b>:00–999',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/origin-to-adf':{
                'origin-to-adf': 'Origin to ADF',
                'webui:description': ' Specifies the value of the origin parameter in the request URL sent to the ADF.',
                'webui:runtime': 'no'
           },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/min-local-serviceid':{
                 'min-local-serviceid': 'Min Local Service ID',
                 'webui:description': 'Specifies the lower limit of the range of the local eMBMS Service ID. The Local Service ID range is used to identify local eMBMS sessions.<br><b>Value Range</b>:000000–0FFFFF</b><br><b>Note</b>:The Min Local Serviceid should be less than the Max Local Serviceid or equal to the Max Local Serviceid.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/max-local-serviceid':{
                 'max-local-serviceid': 'Max Local Service ID',
                 'webui:description': 'Specifies the upper limit of the range of the local eMBMS Service ID. The Local Service ID range is used to identify local eMBMS sessions.<br><b>Value Range</b>:000000–0FFFFF</b><br><b>Note</b>:The Max Local Serviceid should be larger than the Min Local Serviceid or equal to the Min Local Serviceid.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/min-regional-serviceid':{
                 'min-regional-serviceid': 'Min Regional Service ID',
                 'webui:description': 'Specifies the lower limit of the range of the Regional eMBMS Service ID. The Regional Service ID range is used to identify regional eMBMS sessions.<br><b>Value Range</b>:100000–EFFFFF</b><br><b>Note</b>:The Min Region Serviceid should be less than the Max Region Serviceid or equal to the Max Region Serviceid.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/max-regional-serviceid':{
                 'max-regional-serviceid': 'Max Regional Service ID',
                 'webui:description': 'Specifies the upper limit of the range of the Regional eMBMS Service ID. The Regional Service ID range is used to identify regional eMBMS sessions.<br><b>Value Range</b>:100000–EFFFFF</b><br><b>Note</b>:The Max Region Serviceid should be larger than the Min Region Serviceid or equal to the Min Region Serviceid.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/min-national-serviceid':{
                 'min-national-serviceid': 'Min National Service ID',
                 'webui:description': 'Specifies the lower limit of the range of the National eMBMS Service ID. The National Service ID range is used to identify national eMBMS sessions.<br><b>Value Range</b>:F00000–FFFFFF</b><br><b>Note</b>:The Min National Serviceid should be less than the Max National Serviceid or equal to the Max National Serviceid.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/max-national-serviceid':{
                 'max-national-serviceid': 'Max National Service ID',
                 'webui:description': 'Specifies the upper limit of the range of the National eMBMS Service ID. The National Service ID range is used to identify national eMBMS sessions.<br><b>Value Range</b>:F00000–FFFFFF</b><br><b>Note</b>:The Max National Serviceid should be larger than the Min National Serviceid or equal to the Min National Serviceid.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/BmscName':{
                 'BmscName': 'BMSC Name',
                 'webui:description': 'Specifies the BDC name. It is the value of the origin parameter in the request URL sent to the BMC.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/multicast-ip-lower-limit':{
                 'multicast-ip-lower-limit': 'Multicast IP Lower Limit',
                 'webui:description': 'Specifies the lower limit of the Multicast Destination IP address range. The IP version of the Multicast Destination IP address must be aligned with the value of the IpAddressType parameter.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/multicast-ip-upper-limit':{
                 'multicast-ip-upper-limit': 'Multicast IP Upper Limit',
                 'webui:description': 'Specifies the upper limit of the Multicast Destination IP address range. The IP version of the Multicast Destination IP address must be aligned with the value of the IpAddressType parameter.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/min-tsi':{
                 'min-tsi': 'Min TSI',
                 'webui:description': 'Specifies the lower limit of the TSI.<br><b>Note</b>:The Min TSI should be less than the Max TSI or equal to the Max TSI.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/max-tsi':{
                 'max-tsi': 'Max TSI',
                 'webui:description': 'Specifies the upper limit of the TSI.<br><b>Note</b>:The Max TSI should be larger than the Min TSI or equal to the Min TSI.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/min-destination-port':{
                 'min-destination-port': 'Min Destination Port',
                 'webui:description': 'Specifies the lower limit of the destination port range of the inner IP packets. The multicast destination IP address together with the destination port must be globally unique.<br><b>Note</b>:The Min Destination Port should be less than the Max Destination Port or equal to the Max Destination Port.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/max-destination-port':{
                 'max-destination-port': 'Max Destination Port',
                 'webui:description': 'Specifies the upper limit of the destination port range of the inner IP packets. The multicast destination IP address together with the destination port must be globally unique.<br><b>Note</b>:The Max Destination Port should be larger than the Min Destination Port or equal to the Min Destination Port.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/duration-to-data-transfer':{
                 'duration-to-data-transfer': 'Duration To Data Transfer',
                 'webui:description': 'Specifies the time interval between when the MDF-CP sends the RAR start message and when the MDF-UP sends payload packets. This time value is to ensure the network elements have enough time to reserve resources for receiving traffic from the MDF-UP. The value is in seconds.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/duration-to-data-stop':{
                 'duration-to-data-stop': 'Duration To Data Stop',
                 'webui:description': 'Specifies the time interval between when the MDF-CP sends the RAR stop message and when the MDF-UP stops sending payload packets. This time value is to ensure the network elements have enough time to release resources. The value is in seconds.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sesson-conf/mcch-modification-period':{
                 'mcch-modification-period': 'MCCH Modification Period',
                 'webui:description': 'Specifies Multicast Control Channel Modification Period. This value must be the same as the MCCH Modification Period configured in the eNB. The value is in milliseconds. This value must be less than the value of the DurationToDataTransfer parameter.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/overload-protection-conf':{
                 'overload-protection-conf': 'MDF-UP Overload Protection Parameter'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/overload-protection-conf/max-control-fragment-bandwidth':{
                 'max-control-fragment-bandwidth': 'Max Control Fragment Bandwidth',
                 'webui:description': 'Specifies the upper limit of the control fragment bandwidth. <br>This parameter includes the FLUTE header, UDP header, and IPv4 or IPv6 header length, but don\'t include the SYNC header. <br>The value is in kilobits per second.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/embm-conf':{
                 'embm-conf': 'BMC Parameters'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/embm-conf/bmc-event-uri':{
                 'bmc-event-uri': 'BMC Event URI',
                 'webui:description': 'Specifies the root path of the URL exposed by the BMC to receive events from the BDC. The root path includes the HTTP scheme, host, and port. Example: http://<BMC_Address>:port.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sync-conf':{
                 'sync-conf': 'MDF-UP SYNC Parameters'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sync-conf/sync-sequence':{
                 'sync-sequence': 'Sync Sequence',
                 'webui:description': 'When SYNC is enabled, the MDF-UP needs to send SYNC type3 packets at the end of the SYNC sequence. <br>The value is a multiple of 80 ms, such as 80 ms, 160 ms, ..., and 10240 ms. This value must be aligned with the MSP configured in the eNB. This value sets the sequence length in milliseconds, and the suggested values are 80, 160, and 320.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sync-conf/sync-period':{
                 'sync-period': 'Sync Period',
                 'webui:description': 'Specifies the SYNC period time range. When SYNC is enabled, the scheduler needs to send the packet containing the time stamp during the SYNC period. The time stamp is the relative value of the start time of the period. From the eNB point of view, this value must be multiple of SyncSequence, and be the same as the value of synchPeriodLength, which is configured in the eNB. The value is in milliseconds.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/sync-conf/sync-data-common-reference-time':{
                 'sync-data-common-reference-time': 'Sync Data Common Reference Time',
                 'webui:description': 'Specifies the reference time for sending SYNC PDUs. When SYNC is enabled, at the beginning of the eMBMS session, this reference time is used for calculating the first time stamp. <br>This value must be aligned with the eNB.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/raptor-conf':{
                 'raptor-conf': 'MDF-UP FEC Fec1ParaConf Parameters'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/raptor-conf/fec-1w':{
                 'fec-1w': 'Fec 1W',
                 'webui:description': 'Target on the sub-block size.<br>During the Raptor R10 FEC encoding of a source file, the file is broken into one or more blocks, known as source blocks, which are then encoded by the FEC algorithm. Each source block can be divided into a number of sub-blocks that are small enough to be decoded in the working memory.<br>The value is in bytes.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/raptor-conf/fec-kmax':{
                 'fec-kmax': 'Fec Kmax',
                 'webui:description': 'Specifies a maximum target on the number of symbols per source block.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/raptorq-conf':{
                 'raptorq-conf': 'MDF-UP FEC Fec6ParaConf Parameters'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/raptorq-conf/fec-6w':{
                 'fec-6w': 'Fec6 W',
                 'webui:description': 'Target on the sub-block size.<br>During the RaptorQ FEC encoding of a source file, the file is broken into one or more blocks, known as source blocks, which are then encoded by the FEC algorithm. Each source block can be divided into a number of sub-blocks that are small enough to be decoded in the working memory.<br>The value is in bytes.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/raptorq-conf/fec-ss':{
                 'fec-ss': 'Fec SS',
                 'webui:description': 'The desired lower bound for this parameter, the sub-symbol size, is Fec6SS * Fec6Al.',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/SplitBMSC-conf':{
                 'SplitBMSC-conf': 'MDF-CP ADF Parameters'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/SplitBMSC-conf/file-repair-server-url-root-path':{
                 'file-repair-server-url-root-path': 'File Repair Server Url Root Path',
                 'webui:description': 'Specifies the root path of the URL exposed by the ADF-Provisioning server when creating the file repair service. Example:https://[fd33:ad36:fb3e:4312::12e8]:8443/bm-sc/adf-provisioning/ . This parameter can include multiple URLs, which are separated by commas ",".',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/SplitBMSC-conf/reception-report-server-url-root-path':{
                 'reception-report-server-url-root-path': 'Reception Report Server Url Root Path',
                 'webui:description': 'Specifies the root path of the URL exposed by the ADF-Provisioning server when creating the file repair service. Example:https://[fd33:ad36:fb3e:4312::12e8]:8443/bm-sc/adf-provisioning/ . This parameter can include multiple URLs, which are separated by commas ",".',
                 'webui:runtime': 'yes'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/mdf-up-transmssion':{
                 'mdf-up-transmssion': 'MDF-UP Transmission Parameter'
            },
            '/ncs:services/service/type/bdc-ncm-service/mdf-ncm/mdf-up-transmssion/max-ue-clock-drift':{
                 'max-ue-clock-drift': 'Max Ue Clock Drift',
                 'webui:description': 'Specifies the maximum clock drift between the MDF-UP and the UE.<br>The value is in milliseconds.',
                 'webui:runtime': 'no'
            },
            '/ncs:services/properties/bdc-devices':{
            	'bdc-devices': 'BDC Devices',
                'info': 'A list of BDC devices',
            },

            '/ncs:services/properties/bdc-devices/bdc-device/GW-Addr':{
                'GW-Addr': 'Gateway Address',

            },

            '/ncs:services/properties/bdc-devices/bdc-device/mdf-cp-device':{
                'mdf-cp-device': 'MDF-CP Device',

            },

            '/ncs:services/properties/bdc-devices/bdc-device/mdf-cp-device/ssl':{
                'ssl': 'SSL'

            },

            '/ncs:services/properties/bdc-devices/bdc-device/mdf-ncm-device':{
                'mdf-ncm-device': 'MDF-NCM Device',
            },

            '/ncs:services/properties/bdc-devices/bdc-device/mdf-ncm-device/ssl':{
                'ssl': 'SSL'

            },

        },
        sv_SE: {
            'General': 'Allm�nt',
            'type/broadcast-service/description': 'Beskrivning',
            'type/broadcast-service/broadcast-area': 'S�ndningsomr�de',
            'type/broadcast-service/announce': 'P�annonsera'

        }
    };
});

