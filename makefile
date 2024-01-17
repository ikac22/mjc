# control vars
MY_SYM   = 0

# dirs

BUILD_DIR = build
SRC_DIR   = src/mjc
TOOL_DIR  = tool
SPEC_DIR  = src/spec

# names

LEXER_NAME = Yylex
PARSER_NAME = parser
SYM_NAME = sym
MAIN_NAME = main

# specification files
FLEX_SPEC = $(SPEC_DIR)/$(LEXER_NAME).flex
CUP_SPEC  = $(SPEC_DIR)/$(PARSER_NAME).cup

# code to compile
JAVA_CODE = $(SRC_DIR)/main.java

# toolpaths
JF_PATH   = tools/JFlex.jar
CUP_PATH  = tools/cup_v10k.jar

# tools
JF 	  = java -jar $(JF_PATH)
CUP	  = java -jar $(CUP_PATH)
JAVAC	  = javac

# java classpath
JAVA_CP   = $(BUILD_DIR):$(SRC_DIR):$(CUP_PATH)

vpath %.flex $(SPEC_DIR)
vpath %.cup  $(SPEC_DIR)
vpath %.java $(SRC_DIR)

MAIN_PROG  = $(BUILD_DIR)/$(MAIN_NAME).class 

LEXER_DEPS = $(BUILD_DIR)/$(SYM_NAME).class
LEXER_DEPS += $(BUILD_DIR)/$(LEXER_NAME).class

PARSER_DEPS = $(LEXER_DEPS)
PARSER_DEPS += $(BUILD_DIR)/$(PARSER_NAME).class

ALL_DEPS = $(PARSER_DEPS)


all: $(ALL_DEPS) $(MAIN_PROG)

parser: $(PARSER_DEPS) $(MAIN_PROG)

lexer: $(LEXER_DEPS) $(MAIN_PROG)

$(BUILD_DIR)/%.class: %.java makefile | $(BUILD_DIR)
	$(JAVAC) -d $(dir $(@)) -cp $(JAVA_CP) $(<)

$(SRC_DIR)/$(LEXER_NAME).java: $(SPEC_DIR)/$(LEXER_NAME).flex makefile
	$(JF) -d $(dir $(@)) $(<)

$(SRC_DIR)/$(PARSER_NAME).java: $(SPEC_DIR)/$(PARSER_NAME).cup makefile
	$(CUP) -destdir $(dir $(@)) $(<)

$(BUILD_DIR):
	mkdir -p $(BUILD_DIR)

clean:
	rm -rf $(BUILD_DIR)
ifeq ($(MY_SYM), 0)
	rm -rf $(SRC_DIR)/$(SYM_NAME).java
endif
	rm -rf $(SRC_DIR)/$(LEXER_NAME).java $(SRC_DIR)/$(PARSER_NAME).java

