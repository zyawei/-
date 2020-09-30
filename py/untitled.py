
def test():
	print(projectPath);
	args = sys.argv
	if len(args)==1:
		print("hello")
	elif len(args)==2:
		print('Hello , %s ' %[args[1]])
	else:
		print ('hello %d ' %len(args))
