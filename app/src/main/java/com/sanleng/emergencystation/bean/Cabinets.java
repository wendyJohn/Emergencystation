package com.sanleng.emergencystation.bean;

import java.util.List;

public class Cabinets {


    /**
     * state : ok
     * cabinetRecordList : [{"uscId":"099aac0cfc5d2245256efcf6e7eb4cd2","uscName":"1号柜","uscState":0,"uscLockState":0,"uscAntennaState":"1","uscUsCode":"bea343090501ada11ec1916e533423e1","uscUscsCode":"4b9aa398d0edf40e87ba8f19adff84c3","usHumidity":"58.4","usTemperature":"26.4"},{"uscId":"bed0046bdb26641972e7be4de4386edc","uscName":"2号柜","uscState":0,"uscLockState":0,"uscAntennaState":"2","uscUsCode":"bea343090501ada11ec1916e533423e1","uscUscsCode":"facb64a07adbb7fc2c5caff63f563327","usHumidity":null,"usTemperature":null},{"uscId":"ed6543dbc2be2ecdcc780635492fc578","uscName":"3号柜","uscState":0,"uscLockState":0,"uscAntennaState":"3","uscUsCode":"bea343090501ada11ec1916e533423e1","uscUscsCode":"27aa50e0887c93e04f7bf02068278d12","usHumidity":null,"usTemperature":null}]
     * statecode : PAGE_DATA_SUCCESS
     * message : 分页数据获取成功
     * universalStore : {"usId":"bea343090501ada11ec1916e533423e1","usName":"1号应急柜","usMacAddress":"96F50941B993","usUserCode1":"0178f32126316230b91e78f321260001","usUserCode2":"应急柜","usTypeCode":"1f9bc00501945a6f75220aeb3fd3027f","usUnitCode":"f73b6f5aa62f4af3a07e0cb3a0bc166a","usBuildingCode":"7c2dac7bcf8d4826975fc3c40ce83298","usFloorCode":"c19cb7da1cd947119f17b112e2d724c5","usRoomCode":"a8adc70a2a784fcea31a87b184b5a4cf","usMonitor1":"5678","usMonitor2":"74123","usLongitude":"121","usLatitude":"234","usState":0}
     */

    private String state;
    private String statecode;
    private String message;
    private UniversalStoreBean universalStore;
    private List<CabinetRecordListBean> cabinetRecordList;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UniversalStoreBean getUniversalStore() {
        return universalStore;
    }

    public void setUniversalStore(UniversalStoreBean universalStore) {
        this.universalStore = universalStore;
    }

    public List<CabinetRecordListBean> getCabinetRecordList() {
        return cabinetRecordList;
    }

    public void setCabinetRecordList(List<CabinetRecordListBean> cabinetRecordList) {
        this.cabinetRecordList = cabinetRecordList;
    }

    public static class UniversalStoreBean {
        /**
         * usId : bea343090501ada11ec1916e533423e1
         * usName : 1号应急柜
         * usMacAddress : 96F50941B993
         * usUserCode1 : 0178f32126316230b91e78f321260001
         * usUserCode2 : 应急柜
         * usTypeCode : 1f9bc00501945a6f75220aeb3fd3027f
         * usUnitCode : f73b6f5aa62f4af3a07e0cb3a0bc166a
         * usBuildingCode : 7c2dac7bcf8d4826975fc3c40ce83298
         * usFloorCode : c19cb7da1cd947119f17b112e2d724c5
         * usRoomCode : a8adc70a2a784fcea31a87b184b5a4cf
         * usMonitor1 : 5678
         * usMonitor2 : 74123
         * usLongitude : 121
         * usLatitude : 234
         * usState : 0
         */

        private String usId;
        private String usName;
        private String usMacAddress;
        private String usUserCode1;
        private String usUserCode2;
        private String usTypeCode;
        private String usUnitCode;
        private String usBuildingCode;
        private String usFloorCode;
        private String usRoomCode;
        private String usMonitor1;
        private String usMonitor2;
        private String usLongitude;
        private String usLatitude;
        private int usState;

        public String getUsId() {
            return usId;
        }

        public void setUsId(String usId) {
            this.usId = usId;
        }

        public String getUsName() {
            return usName;
        }

        public void setUsName(String usName) {
            this.usName = usName;
        }

        public String getUsMacAddress() {
            return usMacAddress;
        }

        public void setUsMacAddress(String usMacAddress) {
            this.usMacAddress = usMacAddress;
        }

        public String getUsUserCode1() {
            return usUserCode1;
        }

        public void setUsUserCode1(String usUserCode1) {
            this.usUserCode1 = usUserCode1;
        }

        public String getUsUserCode2() {
            return usUserCode2;
        }

        public void setUsUserCode2(String usUserCode2) {
            this.usUserCode2 = usUserCode2;
        }

        public String getUsTypeCode() {
            return usTypeCode;
        }

        public void setUsTypeCode(String usTypeCode) {
            this.usTypeCode = usTypeCode;
        }

        public String getUsUnitCode() {
            return usUnitCode;
        }

        public void setUsUnitCode(String usUnitCode) {
            this.usUnitCode = usUnitCode;
        }

        public String getUsBuildingCode() {
            return usBuildingCode;
        }

        public void setUsBuildingCode(String usBuildingCode) {
            this.usBuildingCode = usBuildingCode;
        }

        public String getUsFloorCode() {
            return usFloorCode;
        }

        public void setUsFloorCode(String usFloorCode) {
            this.usFloorCode = usFloorCode;
        }

        public String getUsRoomCode() {
            return usRoomCode;
        }

        public void setUsRoomCode(String usRoomCode) {
            this.usRoomCode = usRoomCode;
        }

        public String getUsMonitor1() {
            return usMonitor1;
        }

        public void setUsMonitor1(String usMonitor1) {
            this.usMonitor1 = usMonitor1;
        }

        public String getUsMonitor2() {
            return usMonitor2;
        }

        public void setUsMonitor2(String usMonitor2) {
            this.usMonitor2 = usMonitor2;
        }

        public String getUsLongitude() {
            return usLongitude;
        }

        public void setUsLongitude(String usLongitude) {
            this.usLongitude = usLongitude;
        }

        public String getUsLatitude() {
            return usLatitude;
        }

        public void setUsLatitude(String usLatitude) {
            this.usLatitude = usLatitude;
        }

        public int getUsState() {
            return usState;
        }

        public void setUsState(int usState) {
            this.usState = usState;
        }
    }

    public static class CabinetRecordListBean {
        /**
         * uscId : 099aac0cfc5d2245256efcf6e7eb4cd2
         * uscName : 1号柜
         * uscState : 0
         * uscLockState : 0
         * uscAntennaState : 1
         * uscUsCode : bea343090501ada11ec1916e533423e1
         * uscUscsCode : 4b9aa398d0edf40e87ba8f19adff84c3
         * usHumidity : 58.4
         * usTemperature : 26.4
         */

        private String uscId;
        private String uscName;
        private int uscState;
        private int uscLockState;
        private String uscAntennaState;
        private String uscUsCode;
        private String uscUscsCode;
        private String usHumidity;
        private String usTemperature;

        public String getUscId() {
            return uscId;
        }

        public void setUscId(String uscId) {
            this.uscId = uscId;
        }

        public String getUscName() {
            return uscName;
        }

        public void setUscName(String uscName) {
            this.uscName = uscName;
        }

        public int getUscState() {
            return uscState;
        }

        public void setUscState(int uscState) {
            this.uscState = uscState;
        }

        public int getUscLockState() {
            return uscLockState;
        }

        public void setUscLockState(int uscLockState) {
            this.uscLockState = uscLockState;
        }

        public String getUscAntennaState() {
            return uscAntennaState;
        }

        public void setUscAntennaState(String uscAntennaState) {
            this.uscAntennaState = uscAntennaState;
        }

        public String getUscUsCode() {
            return uscUsCode;
        }

        public void setUscUsCode(String uscUsCode) {
            this.uscUsCode = uscUsCode;
        }

        public String getUscUscsCode() {
            return uscUscsCode;
        }

        public void setUscUscsCode(String uscUscsCode) {
            this.uscUscsCode = uscUscsCode;
        }

        public String getUsHumidity() {
            return usHumidity;
        }

        public void setUsHumidity(String usHumidity) {
            this.usHumidity = usHumidity;
        }

        public String getUsTemperature() {
            return usTemperature;
        }

        public void setUsTemperature(String usTemperature) {
            this.usTemperature = usTemperature;
        }
    }
}
