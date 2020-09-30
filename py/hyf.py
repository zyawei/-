#!/usr/bin/env python3
# -*- coding: utf-8 -*-

' 合悦丰 一键生成 SDK '

__author__ = 'zhang yawei'

import sys
import os
import shutil
import time
import zipfile

class GenerationApi(object):
	"""docstring for GenerationApi"""
	def __init__(self, projectPath,sdkPath):
		super(GenerationApi, self).__init__()
		self.projectPath = projectPath
		self.sdkPath = sdkPath

	def demoBuild(self):
		printDividingLine("demo build")
		os.chdir(self.projectPath)
		print(os.getcwd())
		os.system('./gradlew build')
		#buildWork = os.popen('./gradlew build')
		#printLines(buildWork,"build","gradlew build")
		#outLine = 'Call gradlew build ...'
		#while outLine != '':
		#	print('build %s'%outLine.strip())
		#	outLine = buildWork.readline()
		#else:	
		#	print('gradlew build done ! ')

	def demoGitCommit(self,msg ='auto commit'):
		printDividingLine("demo commit")
		os.chdir(self.projectPath)
		outLine = os.popen('git status').readlines();
		if outLine[1].find('nothing to commit')<0:
			printRed("Please commit git !")
			sys.exit()
		else:
			print('check git commit : ok')

	def sdkRefreshDemo(self):
		printDividingLine("sdkRefreshDemo")
		os.chdir(self.sdkPath)	
		projectName = os.path.split(self.projectPath)[1]
		srcGitTree = '%s/.git/' % self.projectPath
		destGitTree = '%s/demo/%s/.git/' % (self.sdkPath, projectName)
		
		#remove old 
		if(os.path.exists(destGitTree)):
			val = shutil.rmtree(destGitTree)
			print("rm dest git :",val)
		
		#cp .git
		val = shutil.copytree(srcGitTree,destGitTree)
		print('cp git : %s' % val)
		#git reset 	

		os.chdir(self.sdkPath+'/demo/'+projectName)
		#print('***** exec : git log --graph -2')	
		os.system('git log --graph -1')
		#print('***** exec : git branch -a')	
		print()
		os.system('git branch -v')
		#print('***** exec : git reset --hard')	
		print()
		os.system('git reset --hard')
		#print('***** exec : git status')
		print()
		os.system('git clean -df')	
		

	def sdkRefreshApk(self):
		# refresh demo apk
		printDividingLine("sdkRefreshApk")
		os.system('rm %s/demo/*.apk' %self.sdkPath)
		projectName = os.path.split(self.projectPath)[1]
		src = '%s/app/build/outputs/apk/debug/app-debug.apk' % self.projectPath
		dest = '%s/demo/%s.apk' % (self.sdkPath,projectName)
		val = shutil.copyfile( src,dest)	
		print('apk:%s'%val)

	def sdkRefreshLibs(self):
		printDividingLine("sdkRefreshLibs")
		os.chdir(self.sdkPath)
		os.chdir('libs')
		print('cd :%s' % os.getcwd())
		#cp jar
		val = shutil.copyfile('%s/app/libs/MxFingerAlgHYF.jar' % self.projectPath ,'%s/MxFingerAlgHYF.jar'%os.getcwd() )
		print('cp :%s' %val)
		libsv7a = '%s/app/libs/armeabi-v7a/' % self.projectPath
		jnisv7a = '%s/app/src/main/jniLibs/armeabi-v7a' % self.projectPath
		jnidest = '%s/obj/armeabi-v7a' %os.getcwd()
		if os.path.exists(jnidest):
			shutil.rmtree(jnidest)
		if os.path.exists(libsv7a):
			val = shutil.copytree(libsv7a,jnidest)
			print('cp :%s' %libsv7a)
		elif os.path.exists(jnisv7a):
			val = shutil.copytree(jnisv7a,jnidest)	
			print('cp :%s' %jnisv7a)
		else:
			printRed('no jni libs')

		os.remove('%s/%s' % (jnidest,'libbiofp_e_lapi.so'))
		os.remove('%s/%s' % (jnidest,'libFingerILA.so'))

	def refreshDoc(self):
		print('pls cp doc pdf .')
	
	def refreshReadme(self):
		printDividingLine("refreshReadme")
		os.chdir(self.sdkPath)
		#readVersion
		versions=[1,0,0] 
		try:
			fp = open('%s/readme.txt' % self.sdkPath,'r')
			lines = fp.readlines();
			for line in lines:
				if line.startswith('Version'):
					versions = line.split(':')[1].strip().split('.')
					break
		except Exception as e:
			printRed("open readme Error ")
		finally:
			fp.close()
		
		fp = open('%s/readme.txt' % self.sdkPath,'w')
		fileTree = os.popen('tree -L 2').readlines()
		
		#write tree
		for index in range(len(fileTree)-1):
			fp.write(fileTree[index])


		#获得当前时间时间戳 
		strTime = time.strftime("%Y%m%d", time.localtime(int(time.time()))) 
		fp.write('##################################\n')
		new_version = int(versions[2])
		if not keepVersion:
			new_version = new_version+1
		fp.write('Version : %s.%s.%d\n' %(versions[0],versions[1],new_version))
		fp.write('Data    : %s\n'%strTime)
		if updateLog!='None' and updateLog!='':
			fp.write('Log 	  : %s\n' % updateLog)	
		fp.close()
		os.system('cat %s/readme.txt' % self.sdkPath)
		return '%s.%s.%d-%s'%(versions[0],versions[1],new_version,strTime)




	def refreshSdkZip(self,versionAndTime):
		printDividingLine("refresh ZipFile ")
		spSdkDir = os.path.split(self.sdkPath);
		os.chdir(spSdkDir[0])
		print('cd :%s' % os.getcwd())
		out_path = '%s/%s-%s.zip'%(os.getcwd(),spSdkDir[1],versionAndTime)
		src_path = r"./"+spSdkDir[1]
		print('zip:%s'%src_path)
		print('des:%s'%out_path)
		zip_file_path(src_path, out_path)
		print('zip:done!')



def get_zip_file(input_path, result):
    """
    对目录进行深度优先遍历
    :param input_path:
    :param result:
    :return:
    """
    files = os.listdir(input_path)
    for file in files:
        if os.path.isdir(input_path + '/' + file):
            get_zip_file(input_path + '/' + file, result)
        else:
            result.append(input_path + '/' + file)

def zip_file_path(input_path, output_path):
    """
    压缩文件
    :param input_path: 压缩的文件夹路径
    :param output_path: 解压（输出）的路径
    :param output_name: 压缩包名称
    :return:
    """
    f = zipfile.ZipFile(output_path, 'w', zipfile.ZIP_DEFLATED)
    filelists = []
    get_zip_file(input_path, filelists)
    for file in filelists:
        f.write(file)
    # 调用了close方法才会保证完成压缩
    f.close()
    return output_path

def printDividingLine(x = 'div '):
	print('*********************************',x,'*********************************',)
def printRed(x = ''):
	print('\033[1;41m Error : %s \033[0m'%x)
def printLog(x=''):
	print(x)
def printLines(stream,head='',cmd=''):
	outLine = '%s : %s : start'%(head,cmd)
	i = 0
	while outLine != '':
		print('%s : %s'%(head,outLine.strip()))
		outLine = stream.readline()
	else:	
		print('%s : done' %head)


LIST_BH502=['/Users/zhangyw/Projects/AndroidProjects/other/hyf/FpSDKSampleP41M2_AndroidStudio', '/Users/zhangyw/ProjectInfo/和悦丰/BH502/MxFingerAlgHYF-BH502-LFD-mp']
PROJECTS = {'BH502':LIST_BH502}


global projectPath,projectPath, updateLog ,showLog,keepVersion

def init():
	globals()['updateLog'] = 'None'
	globals()['showLog'] = False
	globals()['keepVersion'] = False
	
	projectAlias = 'none'
	args = 	sys.argv

	for index in range(len(args)):
		if '-project'==args[index]:
			projectAlias = args[index+1]
		elif '-log'==args[index]:
			globals()['updateLog'] = args[index+1]
		elif '--show-log'==args[index]:
			globals()['showLog'] = True
		elif '--keep-version' == args[index]:
			globals()['keepVersion'] = True		

	
	try:
		globals()['projectPath'] = PROJECTS[projectAlias][0]
		globals()['sdkPath'] = PROJECTS[projectAlias][1]
	except Exception as e:
		printRed('Not found %s' %projectAlias)
		sys.exit()
	else:
		print('Project      :',projectAlias)

	print('Source 		:',projectPath)
	print('SDK    		:',sdkPath)
	print('Log    		:',updateLog)
	print('showLog     	:',showLog)
	print('keepVersion 	:',keepVersion)

def work():
	generationApi = GenerationApi(projectPath,sdkPath)
	#generationApi.demoGitCommit()
	generationApi.demoBuild()
	generationApi.sdkRefreshDemo()
	generationApi.sdkRefreshApk()
	generationApi.sdkRefreshLibs()
	data_time = generationApi.refreshReadme()
	generationApi.refreshSdkZip(data_time)


print('Welcome To Use HYF SDK Generation Tool ')
printDividingLine('info');
init()
work()

