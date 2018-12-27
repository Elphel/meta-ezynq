PROVIDES = "u-boot u-boot-ezynq"

LICENSE = "GPLv2+ & GPLv3"
LIC_FILES_CHKSUM = "file://${GITDIR_ezynq}/LICENSE;md5=891e49b3c2a8c133ffe7985e54245aff"

PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "u-boot-ezynq"

FILES_u-boot-ezynq = "/boot"

GITDIR_ezynq_dir= "git"
GITDIR_ezynq_label = "ezynq"
GITDIR_ezynq= "${WORKDIR}/${GITDIR_ezynq_dir}"
GITDIR_ezynq_bindir = "${GITDIR_ezynq}/images/elphel393"

S = "${GITDIR_ezynq}"
B = "${S}"

ELPHELGITHOST ??= "git.elphel.com"
SRC_URI_ezynq= "https://${ELPHELGITHOST}/Elphel/ezynq.git"
SRC_URI_ezynq_branch= "master"

##########################################################################################
# Defining u-boot.inc parameters

UBOOT_SUFFIX = "img"
UBOOT_IMAGE = "u-boot-${MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY = "u-boot.${UBOOT_SUFFIX}"
UBOOT_SYMLINK = "u-boot-${MACHINE}.${UBOOT_SUFFIX}"
UBOOT_MAKE_TARGET = "all"

SPL_SUFFIX = "bin"
SPL_BINARY = "boot.${SPL_SUFFIX}"
SPL_IMAGE = "boot-${MACHINE}-${PV}-${PR}.${SPL_SUFFIX}"
SPL_SYMLINK = "boot-${MACHINE}.${SPL_SUFFIX}"

# Tasks

# partially from u-boot-ezynq-tasks.inc

# when updating from Jethro to Rocko 'cleandirs' is set for S
# So it happily nukes a freshly cloned u-boot repo, so:
# unsetting cleandirs flag:

python(){
  d.setVarFlag('do_unpack','cleandirs', '')
}

##########################################################################################
# SRC_URI - Couldn't find how to deal with multiple git repos:
# 1. Tried and it didn't work: http://lists.linuxtogo.org/pipermail/openembedded-core/2011-October/010926.html
# 2. No *.bb examples found
# Overriding do_fetch:

do_fetch(){
  echo "Fetching ezynq..."
  if [ ! -d "${GITDIR_ezynq}/.git" ]; then
    # if anything left after do_clean - delete it
    if [ -d ${GITDIR_ezynq} ]; then
	rm -r ${GITDIR_ezynq}
    fi
    git clone -b ${SRC_URI_ezynq_branch} ${SRC_URI_ezynq} ${GITDIR_ezynq}
    cd ${GITDIR_ezynq}
    git checkout ${SRCREV_ezynq}
    cd ${WORKDIR}
    echo "Cloning ezynq done."
  else
    echo "Skipped: ezynq is already cloned."
  fi
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install(){
    install -d ${D}/boot
    install -m 644 ${GITDIR_ezynq_bindir}/boot.bin ${D}/boot/boot.bin
    install -m 644 ${GITDIR_ezynq_bindir}/u-boot-dtb.img ${D}/boot/u-boot-dtb.img
}

inherit deploy

do_deploy(){
    install -d ${DEPLOYDIR}
    install -m 644 ${GITDIR_ezynq_bindir}/boot.bin ${DEPLOYDIR}/boot.bin
    install -m 644 ${GITDIR_ezynq_bindir}/u-boot-dtb.img ${DEPLOYDIR}/u-boot-dtb.img
    cd ${DEPLOYDIR}
    for RLOC in ${PRODUCTION_ROOT_LOCATION}; do
        if [ ! -d ${RLOC} ]; then
            mkdir ${RLOC}
        fi
        if [ -f ${RLOC}/boot.bin ]; then
            rm ${RLOC}/boot.bin
        fi
        cp boot.bin ${RLOC}/
        if [ -f ${RLOC}/u-boot-dtb.img ]; then
            rm ${RLOC}/u-boot-dtb.img
        fi
        cp u-boot-dtb.img ${RLOC}/u-boot-dtb.img
    done
}

addtask deploy before do_build after do_compile
