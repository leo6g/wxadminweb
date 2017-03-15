'use strict';

var fileinclude = require('gulp-file-include'),
  gulp = require('gulp');

gulp.task('include', function() {
  gulp.src(['static/**/*'])
    .pipe(fileinclude({
      prefix: '@@',
      basepath: '@file'
    }))
    .pipe(gulp.dest('./dist'));
});

gulp.task('watch',function(){
	gulp.watch(['static/**/*','assets/tpl/*'],['include']);
});
gulp.task('dev',['watch']);
gulp.task('default',['include']);
