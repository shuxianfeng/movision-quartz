package com.movision.facade;

import com.movision.utils.propertiesLoader.PropertiesLoader;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/31 15:00
 */
@Service
public class UserFacade {

    public Map<String, Integer> getLev(int point) {
        Map<String, Integer> map = new HashMap<>();
        int lev = 0;
        int uppoint = 0;
        int nextpoint = 0;

        int lev10 = Integer.parseInt(PropertiesLoader.getValue("lev10"));
        int lev20 = Integer.parseInt(PropertiesLoader.getValue("lev20"));
        int lev30 = Integer.parseInt(PropertiesLoader.getValue("lev30"));
        int lev40 = Integer.parseInt(PropertiesLoader.getValue("lev40"));
        int lev50 = Integer.parseInt(PropertiesLoader.getValue("lev50"));
        int lev60 = Integer.parseInt(PropertiesLoader.getValue("lev60"));
        int lev70 = Integer.parseInt(PropertiesLoader.getValue("lev70"));
        int lev80 = Integer.parseInt(PropertiesLoader.getValue("lev80"));
        int lev90 = Integer.parseInt(PropertiesLoader.getValue("lev90"));

        if (point < lev10) {
            int lev1 = Integer.parseInt(PropertiesLoader.getValue("lev1"));
            int lev2 = Integer.parseInt(PropertiesLoader.getValue("lev2"));
            int lev3 = Integer.parseInt(PropertiesLoader.getValue("lev3"));
            int lev4 = Integer.parseInt(PropertiesLoader.getValue("lev4"));
            int lev5 = Integer.parseInt(PropertiesLoader.getValue("lev5"));
            int lev6 = Integer.parseInt(PropertiesLoader.getValue("lev6"));
            int lev7 = Integer.parseInt(PropertiesLoader.getValue("lev7"));
            int lev8 = Integer.parseInt(PropertiesLoader.getValue("lev8"));
            int lev9 = Integer.parseInt(PropertiesLoader.getValue("lev9"));
            if (point < lev1) {
                lev = 0;
                uppoint = 0;
                nextpoint = lev1;
            } else if (point < lev2) {
                lev = 1;
                uppoint = lev1;
                nextpoint = lev2;
            } else if (point < lev3) {
                lev = 2;
                uppoint = lev2;
                nextpoint = lev3;
            } else if (point < lev4) {
                lev = 3;
                uppoint = lev3;
                nextpoint = lev4;
            } else if (point < lev5) {
                lev = 4;
                uppoint = lev4;
                nextpoint = lev5;
            } else if (point < lev6) {
                lev = 5;
                uppoint = lev5;
                nextpoint = lev6;
            } else if (point < lev7) {
                lev = 6;
                uppoint = lev6;
                nextpoint = lev7;
            } else if (point < lev8) {
                lev = 7;
                uppoint = lev7;
                nextpoint = lev8;
            } else if (point < lev9) {
                lev = 8;
                uppoint = lev8;
                nextpoint = lev9;
            } else {
                lev = 9;
                uppoint = lev9;
                nextpoint = lev10;
            }
        } else if (point < lev20) {
            int lev11 = Integer.parseInt(PropertiesLoader.getValue("lev11"));
            int lev12 = Integer.parseInt(PropertiesLoader.getValue("lev12"));
            int lev13 = Integer.parseInt(PropertiesLoader.getValue("lev13"));
            int lev14 = Integer.parseInt(PropertiesLoader.getValue("lev14"));
            int lev15 = Integer.parseInt(PropertiesLoader.getValue("lev15"));
            int lev16 = Integer.parseInt(PropertiesLoader.getValue("lev16"));
            int lev17 = Integer.parseInt(PropertiesLoader.getValue("lev17"));
            int lev18 = Integer.parseInt(PropertiesLoader.getValue("lev18"));
            int lev19 = Integer.parseInt(PropertiesLoader.getValue("lev19"));
            if (point < lev11) {
                lev = 10;
                uppoint = lev10;
                nextpoint = lev11;
            } else if (point < lev12) {
                lev = 11;
                uppoint = lev11;
                nextpoint = lev12;
            } else if (point < lev13) {
                lev = 12;
                uppoint = lev12;
                nextpoint = lev13;
            } else if (point < lev14) {
                lev = 13;
                uppoint = lev13;
                nextpoint = lev14;
            } else if (point < lev15) {
                lev = 14;
                uppoint = lev14;
                nextpoint = lev15;
            } else if (point < lev16) {
                lev = 15;
                uppoint = lev15;
                nextpoint = lev16;
            } else if (point < lev17) {
                lev = 16;
                uppoint = lev16;
                nextpoint = lev17;
            } else if (point < lev18) {
                lev = 17;
                uppoint = lev17;
                nextpoint = lev18;
            } else if (point < lev19) {
                lev = 18;
                uppoint = lev18;
                nextpoint = lev19;
            } else {
                lev = 19;
                uppoint = lev19;
                nextpoint = lev20;
            }
        } else if (point < lev30) {
            int lev21 = Integer.parseInt(PropertiesLoader.getValue("lev21"));
            int lev22 = Integer.parseInt(PropertiesLoader.getValue("lev22"));
            int lev23 = Integer.parseInt(PropertiesLoader.getValue("lev23"));
            int lev24 = Integer.parseInt(PropertiesLoader.getValue("lev24"));
            int lev25 = Integer.parseInt(PropertiesLoader.getValue("lev25"));
            int lev26 = Integer.parseInt(PropertiesLoader.getValue("lev26"));
            int lev27 = Integer.parseInt(PropertiesLoader.getValue("lev27"));
            int lev28 = Integer.parseInt(PropertiesLoader.getValue("lev28"));
            int lev29 = Integer.parseInt(PropertiesLoader.getValue("lev29"));
            if (point < lev21) {
                lev = 20;
                uppoint = lev20;
                nextpoint = lev21;
            } else if (point < lev22) {
                lev = 21;
                uppoint = lev21;
                nextpoint = lev22;
            } else if (point < lev23) {
                lev = 22;
                uppoint = lev22;
                nextpoint = lev23;
            } else if (point < lev24) {
                lev = 23;
                uppoint = lev23;
                nextpoint = lev24;
            } else if (point < lev25) {
                lev = 24;
                uppoint = lev24;
                nextpoint = lev25;
            } else if (point < lev26) {
                lev = 25;
                uppoint = lev25;
                nextpoint = lev26;
            } else if (point < lev27) {
                lev = 26;
                uppoint = lev26;
                nextpoint = lev27;
            } else if (point < lev28) {
                lev = 27;
                uppoint = lev27;
                nextpoint = lev28;
            } else if (point < lev29) {
                lev = 28;
                uppoint = lev28;
                nextpoint = lev29;
            } else {
                lev = 29;
                uppoint = lev29;
                nextpoint = lev30;
            }
        } else if (point < lev40) {
            int lev31 = Integer.parseInt(PropertiesLoader.getValue("lev31"));
            int lev32 = Integer.parseInt(PropertiesLoader.getValue("lev32"));
            int lev33 = Integer.parseInt(PropertiesLoader.getValue("lev33"));
            int lev34 = Integer.parseInt(PropertiesLoader.getValue("lev34"));
            int lev35 = Integer.parseInt(PropertiesLoader.getValue("lev35"));
            int lev36 = Integer.parseInt(PropertiesLoader.getValue("lev36"));
            int lev37 = Integer.parseInt(PropertiesLoader.getValue("lev37"));
            int lev38 = Integer.parseInt(PropertiesLoader.getValue("lev38"));
            int lev39 = Integer.parseInt(PropertiesLoader.getValue("lev39"));
            if (point < lev31) {
                lev = 30;
                uppoint = lev30;
                nextpoint = lev31;
            } else if (point < lev32) {
                lev = 31;
                uppoint = lev31;
                nextpoint = lev32;
            } else if (point < lev33) {
                lev = 32;
                uppoint = lev32;
                nextpoint = lev33;
            } else if (point < lev34) {
                lev = 33;
                uppoint = lev33;
                nextpoint = lev34;
            } else if (point < lev35) {
                lev = 34;
                uppoint = lev34;
                nextpoint = lev35;
            } else if (point < lev36) {
                lev = 35;
                uppoint = lev35;
                nextpoint = lev36;
            } else if (point < lev37) {
                lev = 36;
                uppoint = lev36;
                nextpoint = lev37;
            } else if (point < lev38) {
                lev = 37;
                uppoint = lev37;
                nextpoint = lev38;
            } else if (point < lev39) {
                lev = 38;
                uppoint = lev38;
                nextpoint = lev39;
            } else {
                lev = 39;
                uppoint = lev39;
                nextpoint = lev40;
            }
        } else if (point < lev50) {
            int lev41 = Integer.parseInt(PropertiesLoader.getValue("lev41"));
            int lev42 = Integer.parseInt(PropertiesLoader.getValue("lev42"));
            int lev43 = Integer.parseInt(PropertiesLoader.getValue("lev43"));
            int lev44 = Integer.parseInt(PropertiesLoader.getValue("lev44"));
            int lev45 = Integer.parseInt(PropertiesLoader.getValue("lev45"));
            int lev46 = Integer.parseInt(PropertiesLoader.getValue("lev46"));
            int lev47 = Integer.parseInt(PropertiesLoader.getValue("lev47"));
            int lev48 = Integer.parseInt(PropertiesLoader.getValue("lev48"));
            int lev49 = Integer.parseInt(PropertiesLoader.getValue("lev49"));
            if (point < lev41) {
                lev = 40;
                uppoint = lev40;
                nextpoint = lev41;
            } else if (point < lev42) {
                lev = 41;
                uppoint = lev41;
                nextpoint = lev42;
            } else if (point < lev43) {
                lev = 42;
                uppoint = lev42;
                nextpoint = lev43;
            } else if (point < lev44) {
                lev = 43;
                uppoint = lev43;
                nextpoint = lev44;
            } else if (point < lev45) {
                lev = 44;
                uppoint = lev44;
                nextpoint = lev45;
            } else if (point < lev46) {
                lev = 45;
                uppoint = lev45;
                nextpoint = lev46;
            } else if (point < lev47) {
                lev = 46;
                uppoint = lev46;
                nextpoint = lev47;
            } else if (point < lev48) {
                lev = 47;
                uppoint = lev47;
                nextpoint = lev48;
            } else if (point < lev49) {
                lev = 48;
                uppoint = lev48;
                nextpoint = lev49;
            } else {
                lev = 49;
                uppoint = lev49;
                nextpoint = lev50;
            }
        } else if (point < lev60) {
            int lev51 = Integer.parseInt(PropertiesLoader.getValue("lev51"));
            int lev52 = Integer.parseInt(PropertiesLoader.getValue("lev52"));
            int lev53 = Integer.parseInt(PropertiesLoader.getValue("lev53"));
            int lev54 = Integer.parseInt(PropertiesLoader.getValue("lev54"));
            int lev55 = Integer.parseInt(PropertiesLoader.getValue("lev55"));
            int lev56 = Integer.parseInt(PropertiesLoader.getValue("lev56"));
            int lev57 = Integer.parseInt(PropertiesLoader.getValue("lev57"));
            int lev58 = Integer.parseInt(PropertiesLoader.getValue("lev58"));
            int lev59 = Integer.parseInt(PropertiesLoader.getValue("lev59"));
            if (point < lev51) {
                lev = 50;
                uppoint = lev50;
                nextpoint = lev51;
            } else if (point < lev52) {
                lev = 51;
                uppoint = lev51;
                nextpoint = lev52;
            } else if (point < lev53) {
                lev = 52;
                uppoint = lev52;
                nextpoint = lev53;
            } else if (point < lev54) {
                lev = 53;
                uppoint = lev53;
                nextpoint = lev54;
            } else if (point < lev55) {
                lev = 54;
                uppoint = lev54;
                nextpoint = lev55;
            } else if (point < lev56) {
                lev = 55;
                uppoint = lev55;
                nextpoint = lev56;
            } else if (point < lev57) {
                lev = 56;
                uppoint = lev56;
                nextpoint = lev57;
            } else if (point < lev58) {
                lev = 57;
                uppoint = lev57;
                nextpoint = lev58;
            } else if (point < lev59) {
                lev = 58;
                uppoint = lev58;
                nextpoint = lev59;
            } else {
                lev = 59;
                uppoint = lev59;
                nextpoint = lev60;
            }
        } else if (point < lev70) {
            int lev61 = Integer.parseInt(PropertiesLoader.getValue("lev61"));
            int lev62 = Integer.parseInt(PropertiesLoader.getValue("lev62"));
            int lev63 = Integer.parseInt(PropertiesLoader.getValue("lev63"));
            int lev64 = Integer.parseInt(PropertiesLoader.getValue("lev64"));
            int lev65 = Integer.parseInt(PropertiesLoader.getValue("lev65"));
            int lev66 = Integer.parseInt(PropertiesLoader.getValue("lev66"));
            int lev67 = Integer.parseInt(PropertiesLoader.getValue("lev67"));
            int lev68 = Integer.parseInt(PropertiesLoader.getValue("lev68"));
            int lev69 = Integer.parseInt(PropertiesLoader.getValue("lev69"));
            if (point < lev61) {
                lev = 60;
                uppoint = lev60;
                nextpoint = lev61;
            } else if (point < lev62) {
                lev = 61;
                uppoint = lev61;
                nextpoint = lev62;
            } else if (point < lev63) {
                lev = 62;
                uppoint = lev62;
                nextpoint = lev63;
            } else if (point < lev64) {
                lev = 63;
                uppoint = lev63;
                nextpoint = lev64;
            } else if (point < lev65) {
                lev = 64;
                uppoint = lev64;
                nextpoint = lev65;
            } else if (point < lev66) {
                lev = 65;
                uppoint = lev65;
                nextpoint = lev66;
            } else if (point < lev67) {
                lev = 66;
                uppoint = lev66;
                nextpoint = lev67;
            } else if (point < lev68) {
                lev = 67;
                uppoint = lev67;
                nextpoint = lev68;
            } else if (point < lev69) {
                lev = 68;
                uppoint = lev68;
                nextpoint = lev69;
            } else {
                lev = 69;
                uppoint = lev69;
                nextpoint = lev70;
            }
        } else if (point < lev80) {
            int lev71 = Integer.parseInt(PropertiesLoader.getValue("lev71"));
            int lev72 = Integer.parseInt(PropertiesLoader.getValue("lev72"));
            int lev73 = Integer.parseInt(PropertiesLoader.getValue("lev73"));
            int lev74 = Integer.parseInt(PropertiesLoader.getValue("lev74"));
            int lev75 = Integer.parseInt(PropertiesLoader.getValue("lev75"));
            int lev76 = Integer.parseInt(PropertiesLoader.getValue("lev76"));
            int lev77 = Integer.parseInt(PropertiesLoader.getValue("lev77"));
            int lev78 = Integer.parseInt(PropertiesLoader.getValue("lev78"));
            int lev79 = Integer.parseInt(PropertiesLoader.getValue("lev79"));
            if (point < lev71) {
                lev = 70;
                uppoint = lev70;
                nextpoint = lev71;
            } else if (point < lev72) {
                lev = 71;
                uppoint = lev71;
                nextpoint = lev72;
            } else if (point < lev73) {
                lev = 72;
                uppoint = lev72;
                nextpoint = lev73;
            } else if (point < lev74) {
                lev = 73;
                uppoint = lev73;
                nextpoint = lev74;
            } else if (point < lev75) {
                lev = 74;
                uppoint = lev74;
                nextpoint = lev75;
            } else if (point < lev76) {
                lev = 75;
                uppoint = lev75;
                nextpoint = lev76;
            } else if (point < lev77) {
                lev = 76;
                uppoint = lev76;
                nextpoint = lev77;
            } else if (point < lev78) {
                lev = 77;
                uppoint = lev77;
                nextpoint = lev78;
            } else if (point < lev79) {
                lev = 78;
                uppoint = lev78;
                nextpoint = lev79;
            } else {
                lev = 79;
                uppoint = lev79;
                nextpoint = lev80;
            }
        } else if (point < lev90) {
            int lev81 = Integer.parseInt(PropertiesLoader.getValue("lev81"));
            int lev82 = Integer.parseInt(PropertiesLoader.getValue("lev82"));
            int lev83 = Integer.parseInt(PropertiesLoader.getValue("lev83"));
            int lev84 = Integer.parseInt(PropertiesLoader.getValue("lev84"));
            int lev85 = Integer.parseInt(PropertiesLoader.getValue("lev85"));
            int lev86 = Integer.parseInt(PropertiesLoader.getValue("lev86"));
            int lev87 = Integer.parseInt(PropertiesLoader.getValue("lev87"));
            int lev88 = Integer.parseInt(PropertiesLoader.getValue("lev88"));
            int lev89 = Integer.parseInt(PropertiesLoader.getValue("lev89"));
            if (point < lev81) {
                lev = 80;
                uppoint = lev80;
                nextpoint = lev81;
            } else if (point < lev82) {
                lev = 81;
                uppoint = lev81;
                nextpoint = lev82;
            } else if (point < lev83) {
                lev = 82;
                uppoint = lev82;
                nextpoint = lev83;
            } else if (point < lev84) {
                lev = 83;
                uppoint = lev83;
                nextpoint = lev84;
            } else if (point < lev85) {
                lev = 84;
                uppoint = lev84;
                nextpoint = lev85;
            } else if (point < lev86) {
                lev = 85;
                uppoint = lev85;
                nextpoint = lev86;
            } else if (point < lev87) {
                lev = 86;
                uppoint = lev86;
                nextpoint = lev87;
            } else if (point < lev88) {
                lev = 87;
                uppoint = lev87;
                nextpoint = lev88;
            } else if (point < lev89) {
                lev = 88;
                uppoint = lev88;
                nextpoint = lev89;
            } else {
                lev = 89;
                uppoint = lev89;
                nextpoint = lev90;
            }
        } else {
            int lev91 = Integer.parseInt(PropertiesLoader.getValue("lev91"));
            int lev92 = Integer.parseInt(PropertiesLoader.getValue("lev92"));
            int lev93 = Integer.parseInt(PropertiesLoader.getValue("lev93"));
            int lev94 = Integer.parseInt(PropertiesLoader.getValue("lev94"));
            int lev95 = Integer.parseInt(PropertiesLoader.getValue("lev95"));
            int lev96 = Integer.parseInt(PropertiesLoader.getValue("lev96"));
            int lev97 = Integer.parseInt(PropertiesLoader.getValue("lev97"));
            int lev98 = Integer.parseInt(PropertiesLoader.getValue("lev98"));
            int lev99 = Integer.parseInt(PropertiesLoader.getValue("lev99"));
            int lev100 = Integer.parseInt(PropertiesLoader.getValue("lev100"));
            if (point < lev91) {
                lev = 90;
                uppoint = lev90;
                nextpoint = lev91;
            } else if (point < lev92) {
                lev = 91;
                uppoint = lev91;
                nextpoint = lev92;
            } else if (point < lev93) {
                lev = 92;
                uppoint = lev92;
                nextpoint = lev93;
            } else if (point < lev94) {
                lev = 93;
                uppoint = lev93;
                nextpoint = lev94;
            } else if (point < lev95) {
                lev = 94;
                uppoint = lev94;
                nextpoint = lev95;
            } else if (point < lev96) {
                lev = 95;
                uppoint = lev95;
                nextpoint = lev96;
            } else if (point < lev97) {
                lev = 96;
                uppoint = lev96;
                nextpoint = lev97;
            } else if (point < lev98) {
                lev = 97;
                uppoint = lev97;
                nextpoint = lev98;
            } else if (point < lev99) {
                lev = 98;
                uppoint = lev98;
                nextpoint = lev99;
            } else {
                lev = 99;
                uppoint = lev99;
                nextpoint = lev100;
            }
        }

        map.put("lev", lev);
        map.put("uppoint", uppoint);
        map.put("nextpoint", nextpoint);

        return map;
    }
}
