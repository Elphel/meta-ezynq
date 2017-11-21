DESCRIPTION = "EZYNQ is the Xilinx's bootgen (FSBL) replacement + u-boot. This recipe generates SPL and U-BOOT images"

HOMEPAGE = "https://elphel.com;https://blog.elphel.com"

SECTION = "bootloaders"

# Project Version
PV = "v2016.05"

# Project Revision
PR = "r0"

include u-boot-ezynq.inc

PROVIDES += " u-boot"

# Define the path to the xilinx platform init code/headers
#PLATFORM_INIT_DIR ?= "/usr/src/xilinx-platform-init"

#PLATFORM_INIT_STAGE_DIR = "${STAGING_DIR_HOST}${PLATFORM_INIT_DIR}"

#UBOOT_CONFIG="dtb"
