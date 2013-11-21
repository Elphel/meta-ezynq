require recipes-bsp/u-boot/u-boot.inc

LICENSE = "GPLv2+ & GPLv3"
LIC_FILES_CHKSUM = "file://${GITDIR_uboot}/COPYING;md5=1707d6db1d42237583f50183a5651ecb \
		    file://${GITDIR_ezynq}/LICENSE;md5=891e49b3c2a8c133ffe7985e54245aff"

# working directory
S = "${WORKDIR}/git/${GITDIR_uboot_dir}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

##########################################################################################
# Defining u-boot.inc parameters

# UBOOT_MACHINE is defined in meta-<board>/conf/machine/<board>.conf 
# (e.g., meta-microzed/conf/machine/microzed.conf: UBOOT_MACHINE= zynq_microzed_config) 

UBOOT_SUFFIX = "bin"
UBOOT_IMAGE = "boot-${MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY = "boot.${UBOOT_SUFFIX}"
UBOOT_SYMLINK = "boot-${MACHINE}.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET = "all"

##########################################################################################
# Variables used in the below overridden functions
GITDIR_ezynq_dir= "git"
GITDIR_ezynq_label = "ezynq"
GITDIR_ezynq= "${WORKDIR}/${GITDIR_ezynq_dir}"
GITDIR_ezynq_u-boot-tree= "${GITDIR_ezynq}/u-boot-tree"

GITDIR_uboot_dir= "u-boot-xlnx"
GITDIR_uboot= "${WORKDIR}/git/${GITDIR_uboot_dir}"

SRC_URI_ezynq= "git://git.code.sf.net/p/elphel/ezynq"
SRC_URI_ezynq_branch= "master"

SRC_URI_uboot= "git://github.com/Xilinx/u-boot-xlnx.git"
SRC_URI_uboot_branch= "master-next"

#SRCREV_ezynq= "83340698f9669977b7205aa67e6ca0dd23fb741f"
SRCREV_uboot= "bbd91fc9ae290c31dc52fd8322f43f67ddd39247"

PATCH_NAME= "ezynq.patch" 

include ezynq-u-boot-tasks.inc