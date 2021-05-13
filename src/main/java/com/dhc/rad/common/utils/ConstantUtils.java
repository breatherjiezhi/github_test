package com.dhc.rad.common.utils;

/**
 * 常量类
 *
 * @version 2018-08-13
 */
public class ConstantUtils {

    /**
     * 部门
     */
    public static class Office {
        public static final String OFFICE_ZJ = "12";//质检部门
        public static final String CC3Q = "7b4adf6547bb4cd89aba7721b5253fc4";//仓储3区
        public static final String CC4Q = "97d66e3a31dc4f9da806b424bd798c53";//仓储4区
    }

    /**
     * 角色
     */
    public static class Role {
        public static final String ROLE_RK = "rk";//入库管理员
        public static final String ROLE_QYZG = "qyzg";//区域主管
        public static final String ROLE_KQ = "kq";//库区管理员
        public static final String ROLE_QT = "qt";//齐套人

        public static final String ROLE_AXCPSR = "axcpsr";//配送人
        public static final String ROLE_BXCPSR = "bxcpsr";//配送人
        public static final String ROLE_CXCPSR = "cxcpsr";//配送人
        public static final String ROLE_DXCPSR = "dxcpsr";//配送人


        public static final String ROLE_GYS = "sys_supplier"; //供应商
        public static final String ROLE_ADMIN = "dept";        //系统最高管理员
    }

    /**
     * 单号编码
     */
    public static class Encoded {
        public static final String enCode = "PR";//入库单
        public static final String ArrivalCode = "DH";//到货单
        public static final String DeliveryCode = "CK";//到货单
        public static final String PDCode = "PD";//盘点
        public static final String DBMCode = "DBM";//物料调拨单
        public static final String DBTCode = "DBT";//工具调拨单
        public static final String TSCode = "T/S";//TCA/SR工单
        public static final String YFCode = "PM";//计划工单
        public static final String JGCode = "MD";//计划工单

    }

    /**
     * 通知
     */
    public static class Notify {

        /**
         * 阅读标记（0：未读；1：已读）
         */
        public static final String READ_FLAG_YES = "1";
        public static final String READ_FLAG_NO = "0";

        /**
         * 通知类型：1 全员通知 2 供应商 3 物流商
         */
        public static final String TYPE_ALL = "1"; //全员通知
        public static final String TYPE_SUPPLIER = "2"; //供应商
        public static final String TYPE_CARRIER = "3"; //物流商
        public static final String TYPE_STOREKEEPER = "4"; //中心库库管
        public static final String TYPE_JOBTEAM = "5"; //中心库作业队


    }

    public static class Approve {
        public static final String INITIALIZATION = "00"; //初始化
        public static final String COMMITED = "01";        //审核中
        public static final String APPROVED = "02";        //已审核
        public static final String NOT_APPROVED = "03";    //已驳回
    }

    public static class Purchase {
        public static final String INITIALIZATION = "00";   //初始化
        public static final String INVOICING = "01";        //发货中
        public static final String PACKING = "02";            //待装箱
        public static final String PACKED = "03";           //装箱中
        public static final String COMPLETED = "04";        //已齐套
        public static final String STORED = "05";           //已入库
        public static final String EXWARING = "06";         //已生成出库单
        public static final String EXWARED = "07";          //已出库
        public static final String DECLARE = "08";            //已报关
        public static final String CLEAR = "09";            //已清关
        public static final String DELIVERY = "10";         //已收货

    }

    public static class Exwarehouse {
        public static final String INITIALIZATION = "00";    //初始化
        public static final String EXWARED = "01";            //已出库
        public static final String DECLEADED = "02";        //已报关
        public static final String CLEARED = "03";            //已清关
        public static final String DELIVERYED = "04";       //已收货
    }


    /**
     * 入库单状态
     */
    public static class WarehouseBill {
        public static final String INIT = "00";                //初始化
        public static final String COMPLETED = "01";        //已齐套
        public static final String INVOICING = "02";        //已发货
        public static final String SIGNIN = "03";            //已收货
        public static final String STORED = "04";            //已入库
    }

    /**
     * 发货单状态
     */
    public static class InvoiceBill {
        public static final String INIT = "00";                //初始化
        public static final String COMPLETED = "01";        //已齐套
        public static final String INVOICING = "02";        //已发货
        public static final String SIGNIN = "03";            //已收货
        public static final String STORED = "04";            //已入库
    }

    /**
     * 装箱单状态
     */
    public static class PackageBill {
        public static final String INIT = "00";                //装箱中
        public static final String COMPLETED = "01";        //已齐套
        public static final String INVOICING = "02";        //已发货

        // 后面的状态保留待用
        public static final String SIGNIN = "03";            //已收货
        public static final String STORED = "04";            //已入库
        public static final String COMP = "05";            //出库单齐套
        public static final String EXWARED = "06";        //已出库

    }


    /**
     * 流水状态
     */
    public static class RecordType {
        public static final String IN = "00";        //入库
        public static final String OUT = "01";        //出库
    }


    /**
     * 盘点
     */
    public static class Inventory {
        public static final String INIT = "00";            //初始化
        public static final String START = "01";        //已启动
        public static final String COMPLETED = "02";    //已完成

        public static final String LOSS = "00";            //盘亏
        public static final String NORMAL = "01";        //正常
        public static final String FILLED = "02";        //盘盈

        public static final String QBPD = "00";            //正常盘点
        public static final String YDPD = "01";            //异动盘点

        public static final String YES = "1";            //盘点平账申请 - 是
        public static final String NO = "0";            //盘点平账申请 - 否

    }

    public static class Requisition {
        public static final String INIT = "00";            //初始化
        public static final String COMP = "01";        //已齐套
        public static final String COMPZ = "02";        //转序齐套
        public static final String HAND = "03";     //已交接
        public static final String HANDZ = "04";    //转序交接
    }


    /**
     * 工序
     */
    public static class WpRuntime {

        public static final String STATUS_UNRUN = "0";//正常

    }

    /**
     * 备料计划单子表
     */
    public static class PickmB {

        public static final String storcode = "X123";//新区紧固件库项目仓库编码
        public static final String stordocid = "1006A3100000000GZ2VB";//新区紧固件库项目仓库编码

    }

    /**
     * 四日缺件
     */
    public static class ShortAnaly {

        public static final String officeid = "1006AA1000000000034F";//采购部id

    }


    /**
     * 请求返回码
     */
    public static class ResCode {
        /** 200 */
        /**
         * 请求 成功
         */
        public static final String SUCCESS = "200";
        /**
         * 请求成功，无数据
         */
        public static final String NODATA = "204";
        /**
         * 请求成功，车辆超过5年
         */
        public static final String PRC = "209";


        /** 900 */

        /**
         * 参数传递异常
         */
        public static final String PARMERROR = "900";

        /**
         * 权限不够
         */
        public static final String PASSLIMITS = "901";

        /**
         * IMEI 错误
         */
        public static final String IMEIISWORNG = "902";
        /**
         * 文件存储异常
         */
        public static final String FILEERROR = "903";
        /**
         * 文件传输异常
         */
        public static final String FILEPOSTERROR = "904";

        /**
         * 协议错误
         */
        public static final String BADREQUEST = "905";
        /**
         * 重复注册
         */
        public static final String PHONEERROR = "907";

        /**
         * 齐套 错误
         */
        public static final String KIT_UNDOWN_CODE = "922";
        /**
         * 齐套 错误
         */
        public static final String KIT_UNALLKIT_CODE = "923";
        /**
         * 齐套 错误
         */
        public static final String KIT_UNFIND_CODE = "924";
        /**
         * 齐套 错误
         */
        public static final String KIT_NUMERROR_CODE = "925";

        /**
         * USERID 错误
         */
        public static final String USERIDISWORNG = "906";




        /** 500 */
        /**
         * 服务器内部错误
         */
        public static final String SERVERERROR = "500";

        /** 600 */
        /**
         * 登录成功
         */
        public static final String LOGINSUCCESS = "600";
        /**
         * 登录失败
         */
        public static final String LOGINFAIL = "601";


        public static final String SUCCESSMSG = "请求成功";
        /**
         * 请求成功，无数据
         */
        public static final String NODATAMSG = "请求成功，无数据";

        public static final String PRCMESS = "超过5年，已超过估价年限";

        public static final String ERRORMSG = "系统异常";

        public static final String PASSLIMITSMSG = "权限不足";
        public static final String ParameterException = "参数为空或者参数异常";

        public static final String INFONOEXIST = "信息不存在";

        public static final String DATEEXIST = "日期已存在";

        public static final String ENDDATE = "已过截止时间，不能修改";


        public static final String UPDATEFAIL = "更新失败";
        public static final String INTEGRALNOTENOUGH = "积分不足";


    }

    // 物资审核
    public static class ApproveM {
        public static final String INIT = "00";        //初始化
        public static final String COMMITED = "01";        //审核中
        public static final String PASS = "02";         //审核通过
        public static final String REJECT = "03";      //已驳回
    }


    /**
     * 入库单状态
     */
    public static class WarehouseBill_C {
        public static final String INIT = "00";                //待上架
        public static final String PUTON = "01";            //上架中
        public static final String COMPLETED = "02";        //已完成
        public static final String FEED = "03";                //待补料

    }

    /**
     * 入库单类型
     */
    public static class WarehouseBill_Type {
        public static final String ZCRK = "00";                //正常入库
        public static final String DBRK = "01";                //调拨入库
        public static final String XTZH = "02";                //形态转换

    }


    /**
     * 上架
     */
    public static class PutOnShelves {
        public static final String INIT = "00";                //待上架
        public static final String COMPLETED = "01";        //已完成
    }


    /**
     * 下架
     */
    public static class PullOffShelves {
        public static final String INIT = "00";                //待下架
        public static final String COMPLETED = "01";        //已完成
    }


    /**
     * 下架状态
     */
    public static class PullOffShelvesStatus {
        public static final String INIT = "00";                //未下架
        public static final String COMPLETED = "01";        //已下架
        public static final String KIT = "02";                //已齐套
    }


    /**
     * 下架类型
     */
    public static class PullOffShelves_Type {
        public static final String LLCK = "00";                //领料出库
        public static final String DBCK = "01";                //调拨出库
        public static final String ZXCK = "02";                //转序出库
        public static final String XTZHCK = "03";            //形态转换出库
    }

    /**
     * 调拨单状态
     */
    public static class Adjust {
        public static final String INIT = "00";                //初始化
        public static final String ADJUSTING = "01";        // 调拨中
        public static final String COMPLETED = "02";        //调拨完成
    }

    /**
     * 退库单状态
     */
    public static class Back {
        public static final String INIT = "00";                //待退库
        public static final String COMMITED = "01";    //审核中
        public static final String APPROVED = "02";    //已审核
        public static final String NOT_APPROVED = "03";  //已驳回
        public static final String COMPLETED = "04";  //已完成
    }

    /**
     * 退库单明细状态
     */
    public static class BackDe {
        public static final String INIT = "00";                //待上架
        public static final String COMPLETED = "01";        //已上架
    }

    //出库单状态
    public static class putOut {
        public static final String INIT = "00";                //初始化
        public static final String PULLOFF = "01";            //已下架
        public static final String KIT = "02";                //已齐套
    }

    //出库单明细状态
    public static class putOutDetail {
        public static final String INIT = "00";                //未下架
        public static final String PULLOFF = "01";            //已下架
        public static final String KIT = "02";                //已齐套
    }


    //领料单明细状态
    public static class RequisitionDetail {
        public static final String INIT = "00";                //初始化
        public static final String EXWARED = "01";                //出库完成
        public static final String KIT = "02";                //已齐套
    }

    //形态转换单明细状态
    public static class ShiftDetail {
        public static final String EX = "00";                //出
        public static final String EN = "01";                //入
    }

    /*审核单状态*/
    public static class freezeStatus {
        public static final String INITIALIZATION = "00"; //初始化
        public static final String COMMITED = "01";        //冻结中(审核中)
        public static final String APPROVED = "02";        //已冻结
        public static final String RELEASE = "03";      //解冻中(审核中)
        public static final String RELEASED = "04";     //已解冻
        public static final String NOT_APPROVED = "05";    //已驳回
    }

    public static class Crecord {
        //‘00’正常入库 ‘01’领料单出库 ‘02’调拨入库 ‘03’ 调拨出库 ‘04’形态转换入库 ‘05’形态转换出库 ‘06’盘点过账入库 ‘07’盘点过账出库 ‘08’异常处理 '09退料入库'
        public static final String ENWARE = "00";
        public static final String EXWARE = "01";
        public static final String ADJUSTIN = "02";
        public static final String ADJUSTOUT = "03";
        public static final String SHIFTIN = "04";
        public static final String SHIFTOUT = "05";
        public static final String PDIN = "06";
        public static final String PDOUT = "07";
        public static final String BUG = "08";
        public static final String BACK = "09";
    }

    /**
     * 生成代码标识
     */
    public static final String code_making = "1";//生成
    public static final String code_not_make = "0";//不生成
}
