# meta-microzed
***
This *README* file contains information on using the *meta-microzed* BSP layer.

The [MicroZed board](http://www.microzed.org/) is an evaluation board by Avnet for the Zynq architecture.
	
Please refer to the *meta-ezynq/README* for details regarding patch submission,
layer dependencies, build process, etc.

***
## Supported Boards/Machines
* **Avnet MicroZed (Zynq) - "microzed"**

## Reference files and documents
[http://www.microzed.org](http://www.microzed.org)

## SD Card Boot
**Note:** This boot flow generates a bootable **boot.bin**.

1. Copy **boot.bin** onto the SD Card.
 * (might not work) Copy the following to the SD card (ensure to rename the files where appropriate):
 *  **uImage** - kernel
 *  **uramdisk.image.gz** (core-image-minimal-<machine name>.ext2.gz.u-boot) - RootFS
 *  **devicetree.dtb** (uImage-<machine name>.dtb) - Device Tree Blob
* Insert SD Card, connect UART to Terminal program and boot board (Ensure the
board is configured for SD Boot).
* Once in u-boot command line - to boot *uImage* type:

        ezynq> boot
