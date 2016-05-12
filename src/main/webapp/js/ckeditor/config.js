/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.toolbar_Basic = [
			[ 'Maximize', '-', 'Source', 'Preview'],
			[ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript' ],
			[ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
			[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
			[ 'Styles', 'Format', 'Font', 'FontSize', 'TextColor', 'BGColor' ] ];
	config.resize_enabled = false;
};
