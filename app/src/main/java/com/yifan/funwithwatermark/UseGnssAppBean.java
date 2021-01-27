package com.yifan.funwithwatermark;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

/**
 * @Description:
 * @Author: ZhengXiang Sun
 * @Data: 2021-01-08
 */
class UseGnssAppBean {

    String packageName;

    String report_interval;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UseGnssAppBean that = (UseGnssAppBean) o;
        return Objects.equals(packageName, that.packageName) &&
                Objects.equals(report_interval, that.report_interval);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(packageName, report_interval);
    }
}
