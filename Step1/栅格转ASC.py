#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2021/5/24 17:26
# @Author  : Xilun Wu
# @email   : nnuwxl@gmail.com
# @File    : 栅格转ASC.py

import arcpy
from arcpy import env
env.workspace = "H:\BaiduNetdiskDownload\投影后DEM"
arcpy.RasterToASCII_conversion("dem_25afterp", "C:\\Users\\Administrator\\Desktop\\dem_25after.asc")