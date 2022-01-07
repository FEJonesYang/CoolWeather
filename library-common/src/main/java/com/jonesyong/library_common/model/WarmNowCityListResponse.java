package com.jonesyong.library_common.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author JonesYang
 * @Data 2021-06-01
 * @Function 有预警信息的城市列表数据实体
 */
public class WarmNowCityListResponse {

    /**
     * code : 200
     * updateTime : 2021-06-01T13:44+08:00
     * warningLocList : [{"locationId":"101020100"},{"locationId":"101020200"},{"locationId":"101020300"},{"locationId":"101020500"},{"locationId":"101020600"},{"locationId":"101020700"},{"locationId":"101020800"},{"locationId":"101020900"},{"locationId":"101021000"},{"locationId":"101021100"},{"locationId":"101021200"},{"locationId":"101050101"},{"locationId":"101050103"},{"locationId":"101050104"},{"locationId":"101050105"},{"locationId":"101050106"},{"locationId":"101050107"},{"locationId":"101050108"},{"locationId":"101050109"},{"locationId":"101050110"},{"locationId":"101050111"},{"locationId":"101050112"},{"locationId":"101050113"},{"locationId":"101050201"},{"locationId":"101050202"},{"locationId":"101050203"},{"locationId":"101050204"},{"locationId":"101050205"},{"locationId":"101050206"},{"locationId":"101050207"},{"locationId":"101050208"},{"locationId":"101050209"},{"locationId":"101050210"},{"locationId":"101050301"},{"locationId":"101050305"},{"locationId":"101050401"},{"locationId":"101050402"},{"locationId":"101050403"},{"locationId":"101050404"},{"locationId":"101050405"},{"locationId":"101050406"},{"locationId":"101050407"},{"locationId":"101050501"},{"locationId":"101050502"},{"locationId":"101050503"},{"locationId":"101050504"},{"locationId":"101050505"},{"locationId":"101050506"},{"locationId":"101050507"},{"locationId":"101050508"},{"locationId":"101050509"},{"locationId":"101050510"},{"locationId":"101050601"},{"locationId":"101050602"},{"locationId":"101050701"},{"locationId":"101050801"},{"locationId":"101050804"},{"locationId":"101050805"},{"locationId":"101050901"},{"locationId":"101050902"},{"locationId":"101050903"},{"locationId":"101050904"},{"locationId":"101050905"},{"locationId":"101051002"},{"locationId":"101051101"},{"locationId":"101051102"},{"locationId":"101051201"},{"locationId":"101051301"},{"locationId":"101051302"},{"locationId":"101051303"},{"locationId":"101051304"},{"locationId":"101051305"},{"locationId":"101060301"},{"locationId":"101060302"},{"locationId":"101060303"},{"locationId":"101060304"},{"locationId":"101060305"},{"locationId":"101060307"},{"locationId":"101060308"},{"locationId":"101060309"},{"locationId":"101060602"},{"locationId":"101060603"},{"locationId":"101060605"},{"locationId":"101070201"},{"locationId":"101070202"},{"locationId":"101070203"},{"locationId":"101070204"},{"locationId":"101070205"},{"locationId":"101070206"},{"locationId":"101070207"},{"locationId":"101080101"},{"locationId":"101080102"},{"locationId":"101080103"},{"locationId":"101080104"},{"locationId":"101080105"},{"locationId":"101080106"},{"locationId":"101080107"},{"locationId":"101080202"},{"locationId":"101080204"},{"locationId":"101080205"},{"locationId":"101080301"},{"locationId":"101080409"},{"locationId":"101080701"},{"locationId":"101080703"},{"locationId":"101080704"},{"locationId":"101080705"},{"locationId":"101080708"},{"locationId":"101080709"},{"locationId":"101080710"},{"locationId":"101080711"},{"locationId":"101080713"},{"locationId":"101081001"},{"locationId":"101081006"},{"locationId":"101081007"},{"locationId":"101081011"},{"locationId":"101081101"},{"locationId":"101081103"},{"locationId":"101090303"},{"locationId":"101090304"},{"locationId":"101090306"},{"locationId":"101180202"},{"locationId":"101180203"},{"locationId":"101180204"},{"locationId":"101180205"},{"locationId":"101180701"},{"locationId":"101180702"},{"locationId":"101180703"},{"locationId":"101180704"},{"locationId":"101180705"},{"locationId":"101180706"},{"locationId":"101180707"},{"locationId":"101180708"},{"locationId":"101180709"},{"locationId":"101180710"},{"locationId":"101180711"},{"locationId":"101180712"},{"locationId":"101180908"},{"locationId":"101181103"},{"locationId":"101181104"},{"locationId":"101181107"},{"locationId":"101181201"},{"locationId":"101181202"},{"locationId":"101181203"},{"locationId":"101181301"},{"locationId":"101181302"},{"locationId":"101181303"},{"locationId":"101181401"},{"locationId":"101100101"},{"locationId":"101100104"},{"locationId":"101100201"},{"locationId":"101100202"},{"locationId":"101100203"},{"locationId":"101100204"},{"locationId":"101100205"},{"locationId":"101100206"},{"locationId":"101100207"},{"locationId":"101100208"},{"locationId":"101100501"},{"locationId":"101100506"},{"locationId":"101100511"},{"locationId":"101100705"},{"locationId":"101100706"},{"locationId":"101100707"},{"locationId":"101100712"},{"locationId":"101100808"},{"locationId":"101100901"},{"locationId":"101100902"},{"locationId":"101100903"},{"locationId":"101100904"},{"locationId":"101100905"},{"locationId":"101100906"},{"locationId":"101101001"},{"locationId":"101101002"},{"locationId":"101101003"},{"locationId":"101101004"},{"locationId":"101101005"},{"locationId":"101101006"},{"locationId":"101101007"},{"locationId":"101101008"},{"locationId":"101101009"},{"locationId":"101101011"},{"locationId":"101101012"},{"locationId":"101101013"},{"locationId":"101101014"},{"locationId":"101101015"},{"locationId":"101101100"},{"locationId":"101120201"},{"locationId":"101120202"},{"locationId":"101120204"},{"locationId":"101120205"},{"locationId":"101120206"},{"locationId":"101120207"},{"locationId":"101120208"},{"locationId":"101120301"},{"locationId":"101120302"},{"locationId":"101120303"},{"locationId":"101120304"},{"locationId":"101120305"},{"locationId":"101120306"},{"locationId":"101120307"},{"locationId":"101120308"},{"locationId":"101120503"},{"locationId":"101120504"},{"locationId":"101120508"},{"locationId":"101120601"},{"locationId":"101120602"},{"locationId":"101120603"},{"locationId":"101120604"},{"locationId":"101120605"},{"locationId":"101120606"},{"locationId":"101120607"},{"locationId":"101120608"},{"locationId":"101120609"},{"locationId":"101120801"},{"locationId":"101120804"},{"locationId":"101230107"},{"locationId":"101230603"},{"locationId":"101230604"},{"locationId":"101230607"},{"locationId":"101230610"},{"locationId":"101230702"},{"locationId":"101230703"},{"locationId":"101230704"},{"locationId":"101230705"},{"locationId":"101230706"},{"locationId":"101230707"},{"locationId":"101240609"},{"locationId":"101240610"},{"locationId":"101240701"},{"locationId":"101240702"},{"locationId":"101240703"},{"locationId":"101240704"},{"locationId":"101240705"},{"locationId":"101240706"},{"locationId":"101240708"},{"locationId":"101240709"},{"locationId":"101240710"},{"locationId":"101240711"},{"locationId":"101240712"},{"locationId":"101240713"},{"locationId":"101240714"},{"locationId":"101240715"},{"locationId":"101240716"},{"locationId":"101240718"},{"locationId":"101220609"},{"locationId":"101221202"},{"locationId":"101280102"},{"locationId":"101280104"},{"locationId":"101280202"},{"locationId":"101280204"},{"locationId":"101280206"},{"locationId":"101280207"},{"locationId":"101280208"},{"locationId":"101280209"},{"locationId":"101280210"},{"locationId":"101280211"},{"locationId":"101280302"},{"locationId":"101280303"},{"locationId":"101280305"},{"locationId":"101280406"},{"locationId":"101280408"},{"locationId":"101280409"},{"locationId":"101280601"},{"locationId":"101280701"},{"locationId":"101280702"},{"locationId":"101280703"},{"locationId":"101280801"},{"locationId":"101280803"},{"locationId":"101280804"},{"locationId":"101280908"},{"locationId":"101281003"},{"locationId":"101281103"},{"locationId":"101281104"},{"locationId":"101281105"},{"locationId":"101281107"},{"locationId":"101281108"},{"locationId":"101281109"},{"locationId":"101281202"},{"locationId":"101281203"},{"locationId":"101281204"},{"locationId":"101281205"},{"locationId":"101281206"},{"locationId":"101281306"},{"locationId":"101281307"},{"locationId":"101281402"},{"locationId":"101281403"},{"locationId":"101281601"},{"locationId":"101281701"},{"locationId":"101281903"},{"locationId":"101281904"},{"locationId":"101282002"},{"locationId":"101282102"},{"locationId":"101300101"},{"locationId":"101300103"},{"locationId":"101300104"},{"locationId":"101300105"},{"locationId":"101300106"},{"locationId":"101300107"},{"locationId":"101300108"},{"locationId":"101300109"},{"locationId":"101300201"},{"locationId":"101300202"},{"locationId":"101300203"},{"locationId":"101300204"},{"locationId":"101300205"},{"locationId":"101300206"},{"locationId":"101300207"},{"locationId":"101300301"},{"locationId":"101300302"},{"locationId":"101300304"},{"locationId":"101300305"},{"locationId":"101300306"},{"locationId":"101300307"},{"locationId":"101300308"},{"locationId":"101300401"},{"locationId":"101300402"},{"locationId":"101300403"},{"locationId":"101300404"},{"locationId":"101300405"},{"locationId":"101300406"},{"locationId":"101300501"},{"locationId":"101300503"},{"locationId":"101300504"},{"locationId":"101300505"},{"locationId":"101300506"},{"locationId":"101300507"},{"locationId":"101300508"},{"locationId":"101300509"},{"locationId":"101300510"},{"locationId":"101300511"},{"locationId":"101300512"},{"locationId":"101300513"},{"locationId":"101300514"},{"locationId":"101300601"},{"locationId":"101300602"},{"locationId":"101300604"},{"locationId":"101300605"},{"locationId":"101300606"},{"locationId":"101300701"},{"locationId":"101300702"},{"locationId":"101300703"},{"locationId":"101300704"},{"locationId":"101300801"},{"locationId":"101300802"},{"locationId":"101300803"},{"locationId":"101300901"},{"locationId":"101300902"},{"locationId":"101300903"},{"locationId":"101300904"},{"locationId":"101300905"},{"locationId":"101300906"},{"locationId":"101301001"},{"locationId":"101301002"},{"locationId":"101301003"},{"locationId":"101301004"},{"locationId":"101301005"},{"locationId":"101301006"},{"locationId":"101301007"},{"locationId":"101301008"},{"locationId":"101301009"},{"locationId":"101301010"},{"locationId":"101301011"},{"locationId":"101301012"},{"locationId":"101301101"},{"locationId":"101301102"},{"locationId":"101301103"},{"locationId":"101301201"},{"locationId":"101301202"},{"locationId":"101301203"},{"locationId":"101301204"},{"locationId":"101301205"},{"locationId":"101301206"},{"locationId":"101301207"},{"locationId":"101301208"},{"locationId":"101301209"},{"locationId":"101301210"},{"locationId":"101301211"},{"locationId":"101301301"},{"locationId":"101301302"},{"locationId":"101301401"},{"locationId":"101301402"},{"locationId":"101301403"},{"locationId":"101301405"},{"locationId":"101310101"},{"locationId":"101310201"},{"locationId":"101310202"},{"locationId":"101310203"},{"locationId":"101310204"},{"locationId":"101310205"},{"locationId":"101310206"},{"locationId":"101310207"},{"locationId":"101310208"},{"locationId":"101310209"},{"locationId":"101310210"},{"locationId":"101310211"},{"locationId":"101310212"},{"locationId":"101310214"},{"locationId":"101310215"},{"locationId":"101310216"},{"locationId":"101310302"},{"locationId":"101310304"},{"locationId":"101310221"},{"locationId":"101310222"},{"locationId":"101260106"},{"locationId":"101260405"},{"locationId":"101260705"},{"locationId":"101260804"},{"locationId":"101260902"},{"locationId":"101260909"},{"locationId":"101290101"},{"locationId":"101290201"},{"locationId":"101290202"},{"locationId":"101290203"},{"locationId":"101290204"},{"locationId":"101290205"},{"locationId":"101290206"},{"locationId":"101290207"},{"locationId":"101290208"},{"locationId":"101290209"},{"locationId":"101290210"},{"locationId":"101290211"},{"locationId":"101290212"},{"locationId":"101290301"},{"locationId":"101290302"},{"locationId":"101290303"},{"locationId":"101290304"},{"locationId":"101290305"},{"locationId":"101290306"},{"locationId":"101290307"},{"locationId":"101290308"},{"locationId":"101290309"},{"locationId":"101290310"},{"locationId":"101290311"},{"locationId":"101290312"},{"locationId":"101290313"},{"locationId":"101290402"},{"locationId":"101290601"},{"locationId":"101290604"},{"locationId":"101290701"},{"locationId":"101290801"},{"locationId":"101290802"},{"locationId":"101290803"},{"locationId":"101290804"},{"locationId":"101290805"},{"locationId":"101290806"},{"locationId":"101290807"},{"locationId":"101290808"},{"locationId":"101290809"},{"locationId":"101290810"},{"locationId":"101290901"},{"locationId":"101290902"},{"locationId":"101290903"},{"locationId":"101290904"},{"locationId":"101290906"},{"locationId":"101290907"},{"locationId":"101290908"},{"locationId":"101290909"},{"locationId":"101290911"},{"locationId":"101290912"},{"locationId":"101291001"},{"locationId":"101291101"},{"locationId":"101291205"},{"locationId":"101291301"},{"locationId":"101291302"},{"locationId":"101291303"},{"locationId":"101291401"},{"locationId":"101291402"},{"locationId":"101291403"},{"locationId":"101291404"},{"locationId":"101291501"},{"locationId":"101291503"},{"locationId":"101291504"},{"locationId":"101291506"},{"locationId":"101291507"},{"locationId":"101291508"},{"locationId":"101270201"},{"locationId":"101270202"},{"locationId":"101270203"},{"locationId":"101270204"},{"locationId":"101270402"},{"locationId":"101271703"},{"locationId":"101271704"},{"locationId":"101111004"},{"locationId":"101170102"},{"locationId":"101170103"},{"locationId":"101170104"},{"locationId":"101170201"},{"locationId":"101170202"},{"locationId":"101170203"},{"locationId":"101170303"},{"locationId":"101160306"},{"locationId":"101160403"},{"locationId":"101160408"},{"locationId":"101161101"},{"locationId":"101161102"},{"locationId":"101161103"},{"locationId":"101161104"},{"locationId":"101161105"},{"locationId":"101161106"},{"locationId":"101161107"},{"locationId":"101161201"},{"locationId":"101161202"},{"locationId":"101161203"},{"locationId":"101161204"},{"locationId":"101161205"},{"locationId":"101161206"},{"locationId":"101161207"},{"locationId":"101161208"},{"locationId":"101150102"},{"locationId":"101130105"},{"locationId":"101130408"},{"locationId":"101130501"},{"locationId":"101130502"},{"locationId":"101130504"},{"locationId":"101130803"},{"locationId":"101130903"},{"locationId":"101130907"},{"locationId":"101130909"},{"locationId":"101130910"},{"locationId":"101131107"},{"locationId":"101131301"},{"locationId":"101131302"},{"locationId":"101131303"},{"locationId":"101131304"},{"locationId":"101131305"},{"locationId":"101131306"},{"locationId":"101131307"},{"locationId":"101131601"},{"locationId":"101131602"},{"locationId":"101131606"},{"locationId":"101080512"},{"locationId":"101020400"},{"locationId":"101021300"},{"locationId":"101021400"},{"locationId":"101021500"},{"locationId":"101021600"},{"locationId":"101021700"},{"locationId":"101050114"},{"locationId":"101050115"},{"locationId":"101050116"},{"locationId":"101050117"},{"locationId":"101050118"},{"locationId":"101050119"},{"locationId":"101050211"},{"locationId":"101050212"},{"locationId":"101050213"},{"locationId":"101050214"},{"locationId":"101050215"},{"locationId":"101050216"},{"locationId":"101050217"},{"locationId":"101050408"},{"locationId":"101050409"},{"locationId":"101050410"},{"locationId":"101050411"},{"locationId":"101050511"},{"locationId":"101050814"},{"locationId":"101050906"},{"locationId":"101050907"},{"locationId":"101050908"},{"locationId":"101050909"},{"locationId":"101050910"},{"locationId":"101051306"},{"locationId":"101051307"},{"locationId":"101051308"},{"locationId":"101051309"},{"locationId":"101060306"},{"locationId":"101070208"},{"locationId":"101070209"},{"locationId":"101070210"},{"locationId":"101070211"},{"locationId":"101080108"},{"locationId":"101080109"},{"locationId":"101080110"},{"locationId":"101080302"},{"locationId":"101080303"},{"locationId":"101080304"},{"locationId":"101081013"},{"locationId":"101100211"},{"locationId":"101100907"},{"locationId":"101101016"},{"locationId":"101110313"},{"locationId":"101120203"},{"locationId":"101120209"},{"locationId":"101120210"},{"locationId":"101120211"},{"locationId":"101120309"},{"locationId":"101120610"},{"locationId":"101120611"},{"locationId":"101120612"},{"locationId":"101120613"},{"locationId":"101120803"},{"locationId":"101120807"},{"locationId":"101130111"},{"locationId":"101130112"},{"locationId":"101131604"},{"locationId":"101170106"},{"locationId":"101170205"},{"locationId":"101180713"},{"locationId":"101180714"},{"locationId":"101181204"},{"locationId":"101181205"},{"locationId":"101181206"},{"locationId":"101181306"},{"locationId":"101230708"},{"locationId":"101240719"},{"locationId":"101270205"},{"locationId":"101270206"},{"locationId":"101280106"},{"locationId":"101280107"},{"locationId":"101280108"},{"locationId":"101280109"},{"locationId":"101280110"},{"locationId":"101280111"},{"locationId":"101280112"},{"locationId":"101280306"},{"locationId":"101280602"},{"locationId":"101280603"},{"locationId":"101280604"},{"locationId":"101280605"},{"locationId":"101280606"},{"locationId":"101280607"},{"locationId":"101280704"},{"locationId":"101280805"},{"locationId":"101281207"},{"locationId":"101282007"},{"locationId":"101290905"},{"locationId":"101291305"},{"locationId":"101291405"},{"locationId":"101291406"},{"locationId":"101291602"},{"locationId":"101300102"},{"locationId":"101300110"},{"locationId":"101300111"},{"locationId":"101300112"},{"locationId":"101300113"},{"locationId":"101300208"},{"locationId":"101300303"},{"locationId":"101300309"},{"locationId":"101300310"},{"locationId":"101300311"},{"locationId":"101300407"},{"locationId":"101300502"},{"locationId":"101300515"},{"locationId":"101300516"},{"locationId":"101300517"},{"locationId":"101300518"},{"locationId":"101300603"},{"locationId":"101300607"},{"locationId":"101300608"},{"locationId":"101300705"},{"locationId":"101300804"},{"locationId":"101300805"},{"locationId":"101300806"},{"locationId":"101300907"},{"locationId":"101300908"},{"locationId":"101301013"},{"locationId":"101301104"},{"locationId":"101301105"},{"locationId":"101301212"},{"locationId":"101301304"},{"locationId":"101301305"},{"locationId":"101301306"},{"locationId":"101301404"},{"locationId":"101300706"},{"locationId":"101310102"},{"locationId":"101310103"},{"locationId":"101310104"},{"locationId":"101310105"},{"locationId":"101310213"},{"locationId":"101310218"},{"locationId":"101310219"},{"locationId":"101310223"},{"locationId":"101310301"},{"locationId":"101101010"},{"locationId":"101280608"},{"locationId":"101280609"}]
     * refer : {"sources":["12379","Weather China"],"license":["no commercial use"]}
     */

    private String code;
    private String updateTime;
    private ReferBean refer;
    private List<WarningLocListBean> warningLocList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ReferBean getRefer() {
        return refer;
    }

    public void setRefer(ReferBean refer) {
        this.refer = refer;
    }

    public List<WarningLocListBean> getWarningLocList() {
        return warningLocList;
    }

    public void setWarningLocList(List<WarningLocListBean> warningLocList) {
        this.warningLocList = warningLocList;
    }

    public static class ReferBean implements Serializable {
        private List<String> sources;
        private List<String> license;

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public List<String> getLicense() {
            return license;
        }

        public void setLicense(List<String> license) {
            this.license = license;
        }

        @Override
        public String toString() {
            return "ReferBean{" +
                    "sources=" + sources +
                    ", license=" + license +
                    '}';
        }
    }

    public static class WarningLocListBean implements Serializable {
        /**
         * locationId : 101020100
         */

        private String locationId;

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        @Override
        public String toString() {
            return "WarningLocListBean{" +
                    "locationId='" + locationId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WarmNowCityListResponse{" +
                "code='" + code + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", refer=" + refer +
                ", warningLocList=" + warningLocList +
                '}';
    }
}
